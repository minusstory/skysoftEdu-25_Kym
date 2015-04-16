/**
 * 0. Project		: hrdkorea
 *
 * 1. FileName		: DisplayTypeTag.java
 * 2. Package		: kr.co.skysoft.pageNavi
 * 3. Author		: soundb@skysoft.co.kr
 * 4. Creation Date	: 2009. 5. 20.
 * 5. Comment		: 
 * 6. History		:
 *                    Name	: Date          : Reason	: Type
 *                   ------------------------------------------------------
 *                    	: 2009. 5. 20.  :			: CREATE
 */
package egovframework.framework.web.taglib;

import javax.servlet.jsp.JspTagException;

import javax.servlet.jsp.tagext.*;

/**
 *
 * <PRE>
 * 1. ClassName		: DisplayTypeTag
 * 2. FileName		: DisplayTypeTag.java
 * 3. Package		: kr.co.skysoft.pageNavi
 * 4. Author		: soundb@skysoft.co.kr
 * 5. Creation Date	: 2009. 5. 20.
 * 6. Comment		: 
 * </PRE>
 */

public class DisplayTypeTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= 130403850182884086L;

	public int doAfterBody() throws JspTagException
	{
		PageNavigationTag	parent	= (PageNavigationTag) findAncestorWithClass(this, PageNavigationTag.class);

		if (parent == null) throw new JspTagException("displayTypeTag - PageNavigation error");
		String	displayType	= getBodyContent().getString().trim();
		parent.setDisplayType(displayType);

		return SKIP_BODY;
	}
}