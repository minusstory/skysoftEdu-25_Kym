package egovframework.framework.servlet.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			HSSFWorkbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String filename = (String) model.get("filename");
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.indexOf("MSIE") > -1){
			filename = URLEncoder.encode(filename, "utf-8");
		}else{
			filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		@SuppressWarnings("unchecked")
		List<Map<String, String>> dataList = (List<Map<String, String>>) model.get("dataList");

		String sheetname = (String) model.get("sheetname");
		HSSFSheet sheet = workbook.createSheet(sheetname);

		HSSFRow header = sheet.createRow(0);

		Map<String, String> labelData = dataList.get(0);
		Set<String> labels = labelData.keySet();
		int rowNum = 0;
		for(String key : labels) {
			header.createCell(rowNum).setCellValue(key);
			rowNum++;
		}

		rowNum = 1;
		int count = 0;
		for(Map<String, String> data : dataList) {
			HSSFRow row = sheet.createRow(rowNum++);
			count = 0;
			for (Map.Entry<String, String> entry : data.entrySet()) {
				row.createCell(count++).setCellValue(entry.getValue());
			}
		}
	}

}
