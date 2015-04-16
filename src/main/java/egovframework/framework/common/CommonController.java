package egovframework.framework.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import egovframework.framework.servlet.view.AlertMessageView;


/**-----------------------------------------------------------------------
 * skysoft edu Project
 *------------------------------------------------------------------------
 * @Class CommonController.java
 * @Description 공통 용도의 Controller
 * @author 한재준
 * @since 2011. 7. 22.
 * @version 1.0
 *
 * @Copyright (c) 2011 한국고용정보원, LG CNS 컨소시엄 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * -----------  ---------  -----------------------------------------------
 * 2011. 7. 22. 한재준       최초생성
 */

@Controller
public class CommonController {

    /**
     * validato rule dynamic Javascript
     */
    @RequestMapping("/validator.do")
    public String validate() {
	return "framework/common/validator.jsp"; // JSP or Tiles definition 처리
    }

    /**
     * closePopupWindow
     */
    @RequestMapping("/closePopupWindow.do")
    public String closePopupWindow(
    		ModelMap model
    		)
    {
	return "framework/common/closePopupWindow.jsp"; // JSP or Tiles definition 처리
    }

    /**
     * Alert Message 처리
     */
    @RequestMapping("/goAndAlertMessage.do")
    public String goAndAlertMessage(
    		ModelMap model
    		)
    {
	return "framework/common/goAndAlertMessage.jsp"; // JSP or Tiles definition 처리
    }


    @RequestMapping("/serverRefresh.do")
    public ModelAndView serverRefresh(
    		@RequestParam(value="inputStr",required=false) String inputStr,
    		ModelMap model
    		)
    {
    	XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) ContextLoader.getCurrentWebApplicationContext();
    	String attr = DispatcherServlet.SERVLET_CONTEXT_PREFIX + "action";
    	//XmlWebApplicationContext xmlWebApplicationContext1 = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext(), attr);
    	XmlWebApplicationContext xmlWebApplicationContext1 = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(xmlWebApplicationContext.getServletContext(), attr);
    	xmlWebApplicationContext.refresh();
    	xmlWebApplicationContext1.refresh();

    	model.addAttribute(AlertMessageView.ALERT_MESSAGE_TYPE, AlertMessageView.ALERT_AND_CLOSE_POPUP);
		model.addAttribute(AlertMessageView.ALERT_MESSAGE_TEXT, inputStr+" 리로드 성공 입니다.");
		return new ModelAndView("alertMessageView");
	}
}