package egovframework.framework.web.taglib;

//import co.skysoft.framework.util.KeisProperties;

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.tag.common.core.UrlSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;
/**-----------------------------------------------------------------------
 * skysoft edu Project
 *------------------------------------------------------------------------
 * @Class SecureUrlTag.java
 * @Description <c:url> 태그와 비슷하게 기능과 같은 기능을하는 <ktag:keisUrl> 기능을 하는 태그라이브러리
 * @author
 * @since
 * @version 1.0
 */

// Referenced classes of package org.apache.taglibs.standard.tag.el.core:
//            ExpressionUtil

public class SecureUrlTag extends UrlSupport
{

    public SecureUrlTag()
    {
        init();
    }

    public int doStartTag()
        throws JspException
    {
        evaluateExpressions();
        return super.doStartTag();
    }

    public void release()
    {
        super.release();
        init();
    }

    public void setValue(String value_)
    {
        this.value_ = value_;
    }

    public void setContext(String context_)
    {
        this.context_ = context_;
    }

    public void setScheme(String scheme)
    {
        this.scheme =  scheme;
    }

    private void init()
    {
        value_ = null;
    }

    private void evaluateExpressions()
        throws JspException
    {
    	/*
    	// local에서 테스트 하는경우는 https가 안되기 때문에 이렇게 한다.
    	if (pageContext.getRequest().getServerName() != null && pageContext.getRequest().getServerName().equals("localhost"))
    		scheme = "http";
    	// 20111215 modified by jjhan
    	if (scheme != null && scheme.equals("https"))
    		urlPrefix = scheme + "://" + pageContext.getRequest().getServerName() +":" + KeisProperties.getProperty("sys.ssl.port");
    	else
    		if(pageContext.getRequest().getServerName().equals("localhost")) urlPrefix = scheme + "://" + pageContext.getRequest().getServerName() +":" + KeisProperties.getProperty("sys.general.port");
    		else urlPrefix = scheme + "://" + pageContext.getRequest().getServerName() +":80";
    	*/

    	/*
    	// 도메인의 이름을 가져온다.
    	String sDomain = pageContext.getRequest().getServerName();

    	// 콘텍스트 처리를 위해 추가함
    	String sContext = KeisProperties.getProperty("sys.context.name");

    	if (sContext == null) sContext ="";
    	else sContext = "/"+sContext;

    	if (sDomain != null && !sDomain.equals("") && !sDomain.equals("localhost")){

    		// 무조건 프로퍼티에 박힌 : www.work.go.kr로 보낸다.
    		sDomain = StringUtil.replace(KeisProperties.getProperty("sys.worknet.url"), "http://", "");

    	}

    	// local에서 테스트 하는경우는 https가 안되기 때문에 이렇게 한다.
    	if (sDomain != null && sDomain.equals("localhost"))
    		scheme = "http";
    	// 20111215 modified by jjhan
    	if (scheme != null && scheme.equals("https"))
    		urlPrefix = scheme + "://" + sDomain +":" + KeisProperties.getProperty("sys.ssl.port");
    	else
    		if(sDomain.equals("localhost")) urlPrefix = scheme + "://" + sDomain +":" + KeisProperties.getProperty("sys.general.port");
    		else urlPrefix = scheme + "://" + sDomain +":80";
    	*/

    	String sDomain = pageContext.getRequest().getServerName();
    	String sContext = null;
    	String sGeneralPort = "8080";

    	if (sContext == null) sContext ="";
    	else sContext = "/"+sContext;

    	if (sGeneralPort == null || sGeneralPort.equals("")) sGeneralPort = "80";

    	// local에서 테스트 하는경우는 https가 안되기 때문에 이렇게 한다.
    	if (sDomain != null && sDomain.equals("localhost"))
    		scheme = "http";
    	// 20111215 modified by jjhan
    	if (scheme != null && scheme.equals("https"))
    		urlPrefix = scheme + "://" + sDomain +":" + "443"+sContext;
    	else
    		if(sDomain.equals("localhost")) urlPrefix = scheme + "://" + sDomain +":" + sGeneralPort +sContext;
    		else urlPrefix = scheme + "://" + sDomain + ":" + sGeneralPort +sContext;

        //value =   (String)ExpressionUtil.evalNotNull("url", "value", value_, java.lang.String.class, this, pageContext);
    	value =   (String)ExpressionUtil.evalNotNull("url", "value", value_, java.lang.String.class, this, pageContext);
    	value =   urlPrefix+value;
        context = (String)ExpressionUtil.evalNotNull("url", "context", context_, java.lang.String.class, this, pageContext);
    }

    private String urlPrefix;
    private String value_;
    private String context_;
    private String scheme; // http or https
}
