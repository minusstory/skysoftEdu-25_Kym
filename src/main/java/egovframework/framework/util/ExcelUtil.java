package egovframework.framework.util;

import egovframework.framework.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

/**-----------------------------------------------------------------------
 * Worknet Project
 *------------------------------------------------------------------------
 * @Class ExcelUtil.java
 * @Description 엑셀파일을 파싱하여 VO List 를 리턴하는 Util Class
 * @author 한재준
 * @since 2011. 11. 11.
 * @version 1.0
 *
 * @Copyright (c) 2011 한국고용정보원, LG CNS 컨소시엄 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 11. 11. 한재준       최초생성
 */
public class ExcelUtil {

	public static Log log = LogFactory.getLog(ExcelUtil.class);

	/**
	 * @param filePath: load할 excel file path.
	 * @param voClass: VO 객체 class.
	 * @param fieldNames: VO 에 로드할 의 field 명 순서
	 * @param headerIndex: 엑셀파일에서 Header 의 Row Index
	 * @return excel file을 load한 List.
	 */
	@SuppressWarnings("unchecked")
	public static List loadExcelFile(String filePath, Class voClass, String[] fieldNames, int headerIndex) throws Exception{
		return loadExcelFile(filePath, voClass, fieldNames, headerIndex, "allSheet");
	}

	@SuppressWarnings("unchecked")
	public static List loadExcelFile(String filePath, Class voClass, String[] fieldNames, int headerIndex, int sheetIndex) throws Exception{
		String idx = "index";
		return loadExcelFile(filePath, voClass, fieldNames, headerIndex, idx + sheetIndex);
	}

	@SuppressWarnings("unchecked")
	public static List loadExcelFile(String filePath, Class voClass, String[] fieldNames, int headerIndex, String sheetName) throws Exception{

		List resultList = new ArrayList();
		FileInputStream fs = null;
		POIFSFileSystem excel = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		int fieldsSize = fieldNames.length;		//Excel file에서 읽어들일 field수.

		//setter method와 parameter Object를 Map 객체에 넣는다.
		Method[] ms = voClass.getMethods();
		HashMap map = new HashMap();

		for(int i=0 ; i < ms.length ; i++){
			if(ms[i].getName().startsWith("set")){
				map.put(ms[i].getName(), ms[i].getParameterTypes()[0]);
			}
		}

		try {
			fs = new FileInputStream(filePath);
			excel = new POIFSFileSystem(fs);
			wb = new HSSFWorkbook(excel);
			int sheetsNum = wb.getNumberOfSheets();
			if(!"allSheet".equals(sheetName)){
				sheetsNum = 1;
			}

			for(int i = 0; i < sheetsNum; i++){
				// sheet 이름이 allSheet 일경우 전체
				if("allSheet".equals(sheetName)){
					sheet = wb.getSheetAt(i);
				// sheet 이름이 index로 시작할 경우 해당 인덱스
				} else if(sheetName.startsWith("index")){
					int index = Integer.parseInt(sheetName.substring(5));
					if(index > wb.getNumberOfSheets() ) throw new Exception("시트 번호가 전체 시트수 보다 많습니다.");
					sheet = wb.getSheetAt(index);

				// 그 외의경우는 시트 이름으로 인식하여 해당 이름의 시트를 가져온다.
				} else {
					sheet = wb.getSheet(sheetName);
				}


				int firstRow = headerIndex;
				int lastRow = sheet.getLastRowNum();

				if (firstRow > lastRow ) throw new Exception("시작행이 저장된 마지막행 보다 작습니다. Load 할 수 없습니다.");


				for(int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++) {
					HSSFRow row = sheet.getRow(rowIndex);
					if(row == null){
						continue;
					}
					int firstCellNum = row.getFirstCellNum();
					int lastCellNum = row.getLastCellNum();
					if((lastCellNum - firstCellNum + 1) < fieldsSize) continue;
					Object voObj;
					voObj = voClass.newInstance();

					for(int cellIndex = firstCellNum; cellIndex <= fieldsSize ; cellIndex++){
						HSSFCell cell = row.getCell(cellIndex);
						if(cell == null || StringUtils.isEmpty(fieldNames[cellIndex])){
							continue;
						}
						//String methodName = "set"+fieldNames[cellIndex].substring(0, 1).toUpperCase() + fieldNames[cellIndex].substring(1);
						//modified by jjhan 20111117
						String methodName = StringUtil.getMethodName("set", fieldNames[cellIndex]);

						Method method = null;
						String paramType = map.get(methodName).toString().toLowerCase();
						if(paramType != null && !"".equals(paramType) && paramType.indexOf("int") > -1){
							// Integer
							method = voObj.getClass().getMethod(methodName, int.class);

							if(cell.getCellType() == Cell.CELL_TYPE_STRING){
								method.invoke(voObj, Integer.parseInt(cell.getRichStringCellValue().toString()));
							}else{
								method.invoke(voObj, Integer.parseInt(String.valueOf(Math.round(cell.getNumericCellValue()))));
							}

						} else if(paramType != null && !"".equals(paramType) && paramType.indexOf("double") > -1){
							// Double
							method = voObj.getClass().getMethod(methodName, double.class);
							method.invoke(voObj, cell.getNumericCellValue());

						} else if(paramType != null && !"".equals(paramType) && paramType.indexOf("float") > -1){
							// Float
							method = voObj.getClass().getMethod(methodName, float.class);
							method.invoke(voObj, (float)cell.getNumericCellValue());

						} else if(paramType != null && !"".equals(paramType) && paramType.indexOf("date") > -1){
							// Date
							method = voObj.getClass().getMethod(methodName, Date.class);
							method.invoke(voObj, cell.getDateCellValue());

						} else {
							// String - Default
							method = voObj.getClass().getMethod(methodName, String.class);
							String pattern = "0";
							DecimalFormat df = new DecimalFormat(pattern);
							if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
								method.invoke(voObj, df.format(cell.getNumericCellValue()));
							}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
								method.invoke(voObj, "");
							}else{
								method.invoke(voObj, cell.getRichStringCellValue().toString());
							}
						}

					}
					resultList.add(voObj);
				}
			}
		} catch (InstantiationException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (FileNotFoundException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (SecurityException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (NoSuchMethodException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (IllegalArgumentException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (IllegalAccessException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (InvocationTargetException e) {
			log.error( e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error( e.getMessage(), e);
			throw e;
		} finally{
			try { fs.close(); } catch (IOException e) { e.printStackTrace(); }
		}

		return resultList;
	}

	@SuppressWarnings("unchecked")
	public static String[] getExcelFieldNamesFromVO(Class voClass) throws Exception{
		String[] fieldNames = null;
		HashMap map = new HashMap();
		//setter method와 parameter Object를 Map 객체에 넣는다.
		Method[] ms = voClass.getMethods();

		// 값 저장 하기 위해 선언함
		int j=0;

		//log.debug("ms.length -->"+ms.length);
		for(int i=0 ; i < ms.length ; i++){
			if(ms[i].getName().startsWith("set")){
				       String tmpStr = ms[i].getName();
				       //log.debug("tmpStr step 0 :  "+i+"-->"+tmpStr );
				       tmpStr = tmpStr.substring(3,tmpStr.length());
				       //log.debug("tmpStr step 1 : "+i+"-->"+tmpStr );
				       tmpStr = tmpStr.substring(0,1).toLowerCase()+tmpStr.substring(1,tmpStr.length());
				       //log.debug("tmpStr step 2 :  "+i+"-->"+tmpStr );
				       //log.debug("tmpStr step 2 :  "+j+"-->"+j);
				       map.put(j, tmpStr);
				j++;
			}
		}
		fieldNames = new String[map.size()];
		for(int i=0;i<map.size();i++){
			fieldNames[i] = (String) map.get(i);
		}

		return fieldNames;
	}
}
