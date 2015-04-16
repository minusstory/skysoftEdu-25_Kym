package egovframework.dev.common.move;


import egovframework.framework.servlet.view.AlertMessageView;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/**-----------------------------------------------------------------------
 * skysoft egov2.5Light Project
 *------------------------------------------------------------------------
 * @Class PageMove.java
 * @Description 페이지 이동하는 ModelAndView 공통정의 하여 사용하기 위한 클래스
 * @author 조상철
 * @since 2011. 9. 24.
 * @version 1.0
 *
 * @Copyright (c) 2012 하늘연소프트 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일              수정자              수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 9. 24.  조상철              최초생성
 */
public class PageMove {

	/**
	 * 메시지 출력후 이전페이지로 돌아간다.
	 *
	 * @param model
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndBack(ModelMap model, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_BACK);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		return new ModelAndView("alertMessageView");
	}


	/**
	 * 메시지 출력후 특정 페이지로 돌아간다.
	 *
	 * @param model
	 * @param url 이동할 페이지
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndRedirect(ModelMap model, String url, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_REDIRECT);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		model.addAttribute(AlertMessageView.REDIRECT_URL, url);
		return new ModelAndView("alertMessageView");
	}


	/**
	 *
	 *
	 * @param request
	 * @param model
	 * @param url 이동할 페이지
	 * @param msg 메시지
	 * @return
	public static ModelAndView httpsToHttpAlertAndRedirect(HttpServletRequest request, ModelMap model, String url, String msg)
	{
		String domain = PageMove.getHttpsToHttpDomain(request, "http");

		if(msg != null) //메시지가 있을경우 메시지 출력후 HTTP로 이동
		{
			model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_REDIRECT);
			model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
			model.addAttribute(AlertMessageView.REDIRECT_URL, domain + url);

			return new ModelAndView("alertMessageView");
		}
		else return new ModelAndView("redirect:" + domain + url); //메시지가 없을경우 HTTP로 리다이렉트
	}
	 */


	/**
	 * HTTP URL을 SSL(Https)로 변경하여 이동한다.
	 *
	 * @param request
	 * @param model
	 * @param url
	 * @param msg
	 * @return
	public static ModelAndView httpToHttpsAlertAndRedirect(HttpServletRequest request, ModelMap model, String url, String msg)
	{
		String domain = PageMove.getHttpsToHttpDomain(request, "https");

		if(msg != null) //메시지가 있을경우 메시지 출력후 HTTP로 이동
		{
			model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_REDIRECT);
			model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
			model.addAttribute(AlertMessageView.REDIRECT_URL, domain + url);

			return new ModelAndView("alertMessageView");
		}
		else return new ModelAndView("redirect:" + domain + url); //메시지가 없을경우 HTTP로 리다이렉트
	}
	 */


	/**
	 * 현재 Request객체의 httpUrl을 가져온다 (개발 DEV Port의경우 해당포트로 반환하면 운영일경우
	 *
	 * @param request
	 * @return
	public static String getHttpsToHttpDomain(HttpServletRequest request, String protocol)
	{
		String domain = protocol + "://";

		String host = request.getServerName();
		//Integer port = request.getServerPort();
		int  port = request.getServerPort();

		String sslPort = KeisProperties.getProperty("sys.ssl.port");
		//String devPort = KeisProperties.getProperty("sys.general.port");

		if(host.equals("localhost"))
		{
			protocol = "http";
			//port = 7001;
			if (KeisProperties.getProperty("sys.general.port") == null)
			port = 80;
			else port = Integer.parseInt(KeisProperties.getProperty("sys.general.port"));
		}
		else port = protocol.equals("https") ? Integer.parseInt(sslPort) : 80;

		domain = protocol + "://" + host + ":" + Integer.toString(port);

		return domain;
	}
	 */


	/**
	 * 메시지 출력후 특정 페이지로 포워드 한다.
	 *
	 * @param model
	 * @param url 포워드할 페이지
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndForward(ModelMap model, String url, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_FORWARD);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		model.addAttribute(AlertMessageView.REDIRECT_URL, url);
		return new ModelAndView("alertMessageView");
	}


	/**
	 * 레이어팝업 사용 : 메시지 출력후 특정 페이지로 돌아간다.
	 *
	 * @param model
	 * @param url 이동할 페이지
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndRedirectLayer(ModelMap model, String url, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_CLOSE_POPUP_LAYER);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		model.addAttribute(AlertMessageView.REDIRECT_URL, url);
		return new ModelAndView("alertMessageView");
	}


	/**
	 * 메시지 출력후 팝업을 닫는다.
	 *
	 * @param model
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndClosePopup(ModelMap model, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_CLOSE_POPUP);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		return new ModelAndView("alertMessageView");
	}


	/**
	 * 레이어팝업 사용 : 메시지 출력후 팝업을 닫는다.
	 *
	 * @param model
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView alertAndClosePopupLayer(ModelMap model, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_CLOSE_POPUP_LAYER);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		return new ModelAndView("alertMessageView");
	}


	/**
	 * 메시지 출력후 팝업을 닫은 후 부모창 리로드
	 *
	 * @param model
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView closePopupAndLoadOpener(ModelMap model, String url, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.CLOSE_POPUP_AND_LOAD_OPENER);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		model.addAttribute(AlertMessageView.REDIRECT_URL, url);

		return new ModelAndView("alertMessageView");
	}


	/**
	 * 메시지 출력후 확인 눌렀을 경우 (js : confirm 과 동일) 특정 페이지로 돌아간다.
	 *
	 * @param model
	 * @param url 이동할 페이지
	 * @param msg 메시지
	 * @return
	 */
	public static ModelAndView confirmAndRedirect(ModelMap model, String url, String msg)
	{
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.CONFIRM_AND_REDIRECT);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, msg);
		model.addAttribute(AlertMessageView.REDIRECT_URL, url);
		return new ModelAndView("alertMessageView");
	}
}
