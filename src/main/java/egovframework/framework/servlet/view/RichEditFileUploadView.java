package egovframework.framework.servlet.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**-----------------------------------------------------------------------
 * skysoft egov2.5Light Project
 *------------------------------------------------------------------------
 * @Class RichEditFileUploadView.java
 * @Description 리치에디터 이미지 첨부파일 업로드 VIEW
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

public class RichEditFileUploadView extends AbstractView{

	private final String DEFAULT_ENCODING = "utf-8"; //인코딩설정
	private final String DEFAULT_MODEL_NAME="filePathName"; //파라미터이름 설정

	private String encoding = DEFAULT_ENCODING;
	private String modelName = DEFAULT_MODEL_NAME;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 리치에디터 파일 업로드 결과 화면을 보여준다.
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
		//Header 설정
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding(this.encoding);

		PrintWriter writer = response.getWriter();
		writer.write((String)model.get(this.modelName));
	}



}
