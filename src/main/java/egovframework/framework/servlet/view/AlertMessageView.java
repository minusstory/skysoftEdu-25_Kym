/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package egovframework.framework.servlet.view;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.util.WebUtils;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class AlertMessageView.java
 * @Description 응답 화면에 Alert Message 를 처리하는 View
 *              Spring 의 InernalResourceView를 인용함
 * @author
 * @since
 * @version 1.0
 */

public class AlertMessageView extends AbstractUrlBasedView{

	// 관련 속성
	public static final String ALERT_MESSAGE_TYPE = "alertMessageType";

	public static final String ALERT_MESSAGE_TEXT = "alertMessageText";

	public static final String REDIRECT_URL = "redirectUrl";

	// Operation Type
	public static final String ALERT_AND_REDIRECT = "alertAndRedirect";

	public static final String ALERT_AND_BACK = "alertAndBack";

	public static final String ALERT_AND_FORWARD = "alertAndForward";

	public static final String ALERT_AND_CLOSE_POPUP = "alertAndClosePopup";

	public static final String CLOSE_POPUP_AND_LOAD_OPENER = "closePopupAndLoadOpener";

	public static final String ALERT_AND_OPENER_RELOAD = "alertAndOpenerReload";

	public static final String CONFIRM_AND_REDIRECT = "confirmAndRedirect";


	// 4개 추가함
	public static final String ALERT_AND_REDIRECT_LAYER = "alertAndRedirectLayer";

	public static final String ALERT_AND_BACK_LAYER = "alertAndBackLayer";

	public static final String ALERT_AND_CLOSE_POPUP_LAYER = "alertAndClosePopupLayer";

	public static final String CLOSE_POPUP_AND_LOAD_OPENER_LAYER = "closePopupAndLoadOpenerLayer";


	private boolean alwaysInclude = false;

	private volatile Boolean exposeForwardAttributes;

	private boolean exposeContextBeansAsAttributes = false;

	private Set<String> exposedContextBeanNames;

	private boolean preventDispatchLoop = false;

    /** sysPropService */
    @Resource(name = "sysPropService")
    public EgovPropertyService sysPropertyService;

    public AlertMessageView()
    {
    	super("/goAndAlertMessage.do");
    }

	/**
	 * An ApplicationContext is not strictly required for InternalResourceView.
	 */
	@Override
	protected boolean isContextRequired() {
		return false;
	}

	/**
	 * Checks whether we need to explicitly expose the Servlet 2.4 request attributes
	 * by default.
	 * @see #setExposeForwardAttributes
	 * @see #exposeForwardRequestAttributes(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void initServletContext(ServletContext sc) {
		if (this.exposeForwardAttributes == null && sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
			this.exposeForwardAttributes = Boolean.TRUE;
		}
	}

	/**
	 * 뷰 처리 구현
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
		//Alert Message 를 띄우기 위한 처리
		setUrl(sysPropertyService.getString("sys.view.alert.url"));

		// Determine which request handle to expose to the RequestDispatcher.
		HttpServletRequest requestToExpose = getRequestToExpose(request);

		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);

		// Expose helpers as request attributes, if any.
		exposeHelpers(requestToExpose);

		// Determine the path for the request dispatcher.
		String dispatcherPath = prepareForRendering(requestToExpose, response);

		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		RequestDispatcher rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		if (rd == null) {
			throw new ServletException("Could not get RequestDispatcher for [" + getUrl() +
					"]: Check that the corresponding file exists within your web application archive!");
		}

		// If already included or response already committed, perform include, else forward.
		if (useInclude(requestToExpose, response)) {
			response.setContentType(getContentType());
			if (logger.isDebugEnabled()) {
				logger.debug("Including resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.include(requestToExpose, response);
		}

		else {
			// Note: The forwarded resource is supposed to determine the content type itself.
			exposeForwardRequestAttributes(requestToExpose);
			if (logger.isDebugEnabled()) {
				logger.debug("Forwarding to resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.forward(requestToExpose, response);
		}
	}


	/**
	 * Get the request handle to expose to the RequestDispatcher, i.e. to the view.
	 * <p>The default implementation wraps the original request for exposure of
	 * Spring beans as request attributes (if demanded).
	 * @param originalRequest the original servlet request as provided by the engine
	 * @return the wrapped request, or the original request if no wrapping is necessary
	 * @see #setExposeContextBeansAsAttributes
	 * @see org.springframework.web.context.support.ContextExposingHttpServletRequest
	 */
	protected HttpServletRequest getRequestToExpose(HttpServletRequest originalRequest) {
		if (this.exposeContextBeansAsAttributes || this.exposedContextBeanNames != null) {
			return new ContextExposingHttpServletRequest(
					originalRequest, getWebApplicationContext(), this.exposedContextBeanNames);
		}
		return originalRequest;
	}

	/**
	 * Expose helpers unique to each rendering operation. This is necessary so that
	 * different rendering operations can't overwrite each other's contexts etc.
	 * <p>Called by {@link #renderMergedOutputModel(Map, HttpServletRequest, HttpServletResponse)}.
	 * The default implementation is empty. This method can be overridden to add
	 * custom helpers as request attributes.
	 * @param request current HTTP request
	 * @throws Exception if there's a fatal error while we're adding attributes
	 * @see #renderMergedOutputModel
	 * @see JstlView#exposeHelpers
	 */
	protected void exposeHelpers(HttpServletRequest request) throws Exception {
	}

	/**
	 * Prepare for rendering, and determine the request dispatcher path
	 * to forward to (or to include).
	 * <p>This implementation simply returns the configured URL.
	 * Subclasses can override this to determine a resource to render,
	 * typically interpreting the URL in a different manner.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return the request dispatcher path to use
	 * @throws Exception if preparations failed
	 * @see #getUrl()
	 */
	protected String prepareForRendering(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String path = getUrl();
		if (this.preventDispatchLoop) {
			String uri = request.getRequestURI();
			if (path.startsWith("/") ? uri.equals(path) : uri.equals(StringUtils.applyRelativePath(uri, path))) {
				throw new ServletException("Circular view path [" + path + "]: would dispatch back " +
						"to the current handler URL [" + uri + "] again. Check your ViewResolver setup! " +
						"(Hint: This may be the result of an unspecified view, due to default view name generation.)");
			}
		}
		return path;
	}

	/**
	 * Obtain the RequestDispatcher to use for the forward/include.
	 * <p>The default implementation simply calls
	 * {@link HttpServletRequest#getRequestDispatcher(String)}.
	 * Can be overridden in subclasses.
	 * @param request current HTTP request
	 * @param path the target URL (as returned from {@link #prepareForRendering})
	 * @return a corresponding RequestDispatcher
	 */
	protected RequestDispatcher getRequestDispatcher(HttpServletRequest request, String path) {
		return request.getRequestDispatcher(path);
	}

	/**
	 * Determine whether to use RequestDispatcher's <code>include</code> or
	 * <code>forward</code> method.
	 * <p>Performs a check whether an include URI attribute is found in the request,
	 * indicating an include request, and whether the response has already been committed.
	 * In both cases, an include will be performed, as a forward is not possible anymore.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return <code>true</code> for include, <code>false</code> for forward
	 * @see javax.servlet.RequestDispatcher#forward
	 * @see javax.servlet.RequestDispatcher#include
	 * @see javax.servlet.ServletResponse#isCommitted
	 * @see org.springframework.web.util.WebUtils#isIncludeRequest
	 */
	protected boolean useInclude(HttpServletRequest request, HttpServletResponse response) {
		return (this.alwaysInclude || WebUtils.isIncludeRequest(request) || response.isCommitted());
	}

	/**
	 * Expose the current request URI and paths as {@link HttpServletRequest}
	 * attributes under the keys defined in the Servlet 2.4 specification,
	 * for Servlet 2.3 containers as well as misbehaving Servlet 2.4 containers
	 * (such as OC4J).
	 * <p>Does not expose the attributes on Servlet 2.5 or above, mainly for
	 * GlassFish compatibility (GlassFish gets confused by pre-exposed attributes).
	 * In any case, Servlet 2.5 containers should finally properly support
	 * Servlet 2.4 features, shouldn't they...
	 * @param request current HTTP request
	 * @see org.springframework.web.util.WebUtils#exposeForwardRequestAttributes
	 */
	protected void exposeForwardRequestAttributes(HttpServletRequest request) {
		if (this.exposeForwardAttributes != null && this.exposeForwardAttributes) {
			try {
				WebUtils.exposeForwardRequestAttributes(request);
			}
			catch (Exception ex) {
				// Servlet container rejected to set internal attributes, e.g. on TriFork.
				this.exposeForwardAttributes = Boolean.FALSE;
			}
		}
	}

}
