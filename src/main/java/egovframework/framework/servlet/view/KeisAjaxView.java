package egovframework.framework.servlet.view;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.view.AbstractView;

import egovframework.framework.util.StringUtil;


/**-----------------------------------------------------------------------
 * skysoft egov2.5Light Project
 *------------------------------------------------------------------------
 * @Class KeisAjaxView.java
 * @Description Aajx 결과 뷰
 * @author 조상철
 * @since 2011. 8. 1.
 * @version 1.0
 *
 * @Copyright (c) 2012 하늘연소프트 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 8. 1. 조상철       최초생성
 */

public class KeisAjaxView extends AbstractView{

	private final String DEFAULT_ENCODING = "utf-8"; //인코딩 설정

	private String encoding = DEFAULT_ENCODING;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Aajx결과 뷰를 생성한다.
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		String ajaxResult = defaultAjaxError();

		//Header 설정
		response.setContentType("application/x-json");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding(this.encoding);

		String listClass = model.get("ajaxResult").getClass().toString();

		if(listClass.indexOf("String") == -1)
		{
			Object ajaxObject = model.get("ajaxResult");

			if(ajaxObject.getClass().getName().endsWith("VO"))
			{
				// 정상적으로 수행 되었을 경우(VO)
				ajaxResult = voToJSonString(ajaxObject);
			}
			else
			{
				// 정상적으로 수행 되었을 경우(List)
				List<Object> resultList = (ArrayList<Object>) model.get("ajaxResult");
				ajaxResult = listToJSonString(resultList);
			}
		}
		else ajaxResult = (String)model.get("ajaxResult");

		PrintWriter writer = response.getWriter();
		writer.write(ajaxResult);
	}


	/**
	 * List형태를 JonsString 으로 변환한다.
	 *
	 * @param lt
	 * @return String
	 * @throws Exception
	 */
	public String listToJSonString(List<Object> lt)
	throws Exception
	{
		JSONArray jsonArray = new JSONArray();

		if("java.util.ArrayList".equals(lt.getClass().getName()))
		{
			for(int i=0 ; i<lt.size() ; i++)
			{
				JSONObject jsonObejct = voToJSONObject(lt.get(i));


				if(!jsonObejct.equals(new JSONObject()))
				{
					jsonArray.add(jsonObejct);
				}
			}
		}

		return jsonArray.toString();
	}


	/**
	 * VO형태를 Json String 으로 변환한다.
	 *
	 * @param ajaxObject
	 * @return
	 * @throws Exception
	 */
	public String voToJSonString(Object ajaxObject)
	throws Exception
	{
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(voToJSONObject(ajaxObject));

		return jsonArray.toString();
	}


	/**
	 * VO를 JSONObject형태로 변환한다.
	 *
	 * @param ajaxObject
	 * @return
	 * @throws Exception
	 */
	public JSONObject voToJSONObject(Object ajaxObject)
	throws Exception
	{
		Class classObject = ajaxObject.getClass();
		JSONObject jsonObject = new JSONObject();

		// Class 명이 VO로 끝나는 경우만 생성한다.
		if(classObject != null && classObject.getSimpleName().endsWith("VO"))
		{
			// Class의 Field 값을 가져온다.
			Field[] classFiled = classObject.getDeclaredFields();

			for(int j=0 ; j < classFiled.length; j++)
			{
				// Method명을 구성한다.
				String fieldName = classFiled[j].getName();
				//String methodName = "get" + fieldName.substring(0, 1).toUpperCase() +  fieldName.substring(1);
				//modified by jjhan 20111117
				String methodName = StringUtil.getMethodName("get", fieldName);


				/*
				if(!"serialVersionUID".equals(fieldName))
				{
					// Method를 가져와 실행한다.(getter)
					Method exeM = classObject.getMethod(methodName, null);
					String returnValue = "";

					if(exeM.invoke( ajaxObject, null) != null)
					{
						returnValue = exeM.invoke( ajaxObject, null).toString();
					}

					// VO의 각 필드, 값들을 JSon Object에 담는다.
					jsonObject.put(fieldName, returnValue);
				}
				*/
			}
		}

		return jsonObject;
	}



	/**
	 * Aajx 에러시 표출한다.
	 *
	 * @return
	 * @throws Exception
	 */
	private String defaultAjaxError()
	throws Exception
	{
		JSONArray errorArray = new JSONArray();
		JSONObject errorObject = new JSONObject();

		errorObject.put("Error", "ErrorOccur");
		errorArray.add(errorObject);

		return errorArray.toString();
	}
}
