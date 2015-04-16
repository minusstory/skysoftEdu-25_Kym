package egovframework.framework.web.savedtoken;

import egovframework.framework.util.NullUtil;

import java.math.BigInteger;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <pre>
 * LSavedToken에 필요한 token의 실제 생성, 비교를 담당한다.
 *
 * </pre>
 *
 * @author DevOn OSF, LG CNS,Inc., devon@lgcns.com
 * @version DevOn OSF 0.5.0
 * @since DevOn OSF 0.5.0
 */
public class SavedToken  {

	protected Log logger = LogFactory.getLog(this.getClass());

	public static final String DEFAULT_TOKEN_NAME = "co.skysoft.token.name";
	public static final String TOKEN_NAME_FIELD = "co.skysoft.token.field";

	private final Random RANDOM = new Random();

	/**
	 * LSavedToken의 default 생성자
	 */
	public SavedToken(){
	}

	/**
	 * random 값을 생성
	 *
	 * @param req
	 * @return BigInteger
	 */
	private String createGUID(HttpServletRequest req) {
		return new BigInteger(165, RANDOM).toString(36).toUpperCase();

	}

	/**
	 * Session에 tokenName을 key로 GUID를 설정한다.
	 *
	 * @param req
	 * @param tokenName
	 * @return
	 */
	private String setToken(HttpServletRequest req, String tokenName, String slot ){
		String guid = null;
		HttpSession session = req.getSession();

		guid = createGUID(req);
		session.setAttribute(TOKEN_NAME_FIELD + slot, tokenName);
		session.setAttribute(tokenName, guid);

		return guid;
	}

	/**
	 * Session과 Request의 GUID 값을 비교한다.
	 *
	 * @param request
	 * @param session
	 * @return result
	 */
	public boolean isValid(HttpServletRequest request, HttpSession session, String tokenNameField) {

		boolean result = false;

		String tokenName = null;
		tokenName = (String)session.getAttribute(tokenNameField);

		if (tokenName != null) {
			String sessionTokenId = (String)session.getAttribute(tokenName);

			String reqTokenId = request.getParameter(tokenName);

			if(NullUtil.notNone(reqTokenId) && NullUtil.notNone(sessionTokenId) ) {
				if(reqTokenId.equals(sessionTokenId)) {
					result = true;
				}
			}

			logger.debug("REQUEST_TOKEN_ID : " + reqTokenId );
			logger.debug("SESSION_TOKEN_ID : " + sessionTokenId );
		}

		session.removeAttribute(tokenNameField);
		session.removeAttribute(tokenName);

		return result;
	}

	/**
	 * 사용자가 전송한 TokenId 를 전달받아서, Session 의 TokenId 와 비교 검증
	 * (검증이 끝나면 Token 을 폐기한다)
	 *
	 * @param reqTokenId
	 * @return
	 */
	public boolean isValid(String reqTokenId, String tokenNameField) {

		boolean result = false;

		// Spring MVC 의 Request Context 에서 request attribute 를 얻는다.
		RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();

		String tokenName = null;
		tokenName = (String)reqAttrs.getAttribute(tokenNameField, RequestAttributes.SCOPE_SESSION);

		if (tokenName != null) {
			String sessionTokenId = (String)reqAttrs.getAttribute(tokenName, RequestAttributes.SCOPE_SESSION);

			if(NullUtil.notNone(reqTokenId) && NullUtil.notNone(sessionTokenId) ) {
				if(reqTokenId.equals(sessionTokenId)) {
					result = true;
				}
			}

			logger.debug("REQUEST_TOKEN_ID : " + reqTokenId );
			logger.debug("SESSION_TOKEN_ID : " + sessionTokenId );
		}

		reqAttrs.removeAttribute(tokenNameField, RequestAttributes.SCOPE_SESSION);
		reqAttrs.removeAttribute(tokenName, RequestAttributes.SCOPE_SESSION);

		return result;
	}

	/**
	 * LTag를 통해 JSP에 입력될 message를 구성한다.
	 *
	 * @param req
	 * @param name
	 * @return
	 */
	public String showHiddenParam( HttpServletRequest req, String name, String slot ) {
		StringBuffer hiddenParam = new StringBuffer("");

		String tokenId = setToken(req, name, slot);

		hiddenParam.append("\n<input type=\"hidden\" name=\"" + TOKEN_NAME_FIELD + slot + "\" value=\"" + name+ "\" />");
		hiddenParam.append("\n<input type=\"hidden\" name=\"" + name + "\" value=\"" + tokenId+ "\" />");

		return hiddenParam.toString();
	}
}