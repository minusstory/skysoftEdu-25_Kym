/**
 * 0. Project		: hrdkorea
 *
 * 1. FileName		: LinkTag.java
 * 2. Package		: kr.co.skysoft.pageNavi
 * 3. Author		: soundb@skysoft.co.kr
 * 4. Creation Date	: 2009. 5. 20.
 * 5. Comment		: 
 * 6. History		:
 *                    Name	: Date          : Reason	: Type
 *                   ------------------------------------------------------
 *                    : 2009. 5. 20.  :			: CREATE
 */
package egovframework.framework.web.taglib;

import javax.servlet.jsp.JspTagException;

import javax.servlet.jsp.tagext.*;

/**
 *
 * <PRE>
 * 1. ClassName		: LinkTag
 * 2. FileName		: LinkTag.java
 * 3. Package		: kr.co.skysoft.pageNavi
 * 4. Author		: soundb@skysoft.co.kr
 * 5. Creation Date	: 2009. 5. 20.
 * 6. Comment		:
 * </PRE>
 */
public class LinkTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= -1597667611691464567L;

	public int doAfterBody() throws JspTagException
    {
		PageNavigationTag	parent	= (PageNavigationTag) findAncestorWithClass(this, PageNavigationTag.class);

		if (parent == null) throw new JspTagException("link - PageNavigation error");
		String	link	= getBodyContent().getString().trim();
		parent.setLink(link);

        return SKIP_BODY;
    }
}