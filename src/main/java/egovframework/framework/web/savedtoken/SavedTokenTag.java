package egovframework.framework.web.savedtoken;

import egovframework.framework.web.savedtoken.SavedToken;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <pre>
 * DevOn Framework의 Page컴포넌트의 UI 부분을 TagLibrary로 구현.
 * form에 hidden으로 포함될 input param의 내용을 생성하고 TagLibrary 자리에 출력한다.
 * </pre>
 *
 * @author DevOn OSF, LG CNS,Inc., devon@lgcns.com
 * @version DevOn OSF 0.5.0
 * @since DevOn OSF 0.5.0
 */
public class SavedTokenTag extends BodyTagSupport{

	private static final long serialVersionUID = 4109742514901126564L;

	private String tokenMessage = "";

	private String name = "";

	private String slot = "";

	/**
	 * LSavedTokenTag 생성자
	 */
	public SavedTokenTag() {
		super();
	}

	@Override
	public int doEndTag() throws JspException {

		String tmdName = getName();
		if (tmdName == null || tmdName.length() == 0) {
			setName(SavedToken.DEFAULT_TOKEN_NAME ); // name으로 지정할 이름   : SavedToken의 default 값으로 지정한다.
		}

		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

		SavedToken token = new SavedToken();

		tokenMessage = token.showHiddenParam(request, getName(), slot);
		this.printTagString(tokenMessage);
		this.release();

		return EVAL_PAGE;
	}

    /**
     * print blank string
     *
     * @throws LTagException
     */
    public void printBlankString() throws Exception{
        printTagString("");
    }

    /**
     * Tag 의 위치에 내용을 출력한다.
     *
     * @param printStr 출력하려는 내용
     * @throws LTagException JspWriter를 이용하던중 IOException이 발생하는 경우.
     */
    public void printTagString(String printStr) throws JspException{
        JspWriter out = this.pageContext.getOut();

        try {
            out.print(printStr);
        } catch (IOException e) {
            throw new JspException("IO Error.", e);
        }
    }

	/**
	 * release
	 */
	@Override
	public void release() {
		super.release();
		this.name = "";
		this.tokenMessage = "";
	}


	/**
	 * name setter
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * name getter
	 */
	public String getName(){
		return this.name;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

}
