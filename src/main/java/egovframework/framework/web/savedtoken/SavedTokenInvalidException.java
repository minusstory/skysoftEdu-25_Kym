package egovframework.framework.web.savedtoken;

import egovframework.rte.fdl.cmmn.exception.BaseException;

/**
 * <pre>
 * 기능 : 저장된 Token 이 유효하지 않을때  발생시키는 예외
 * </pre>
 */
public class SavedTokenInvalidException extends BaseException {

	private static final long serialVersionUID = 7025431540068939668L;

	/**
	 * SavedTokenInvalidException 기본 생성자
	 */
	public SavedTokenInvalidException()
	{
		super("SavedTokenInvalidException without message", null, null);
	}
	
	/**
	 * SavedTokenInvalidException 생성자
	 * @param defaultMessage 메세지 지정 
	 */
	public SavedTokenInvalidException(String defaultMessage)
	{
		super(defaultMessage, null, null);
	}
}
