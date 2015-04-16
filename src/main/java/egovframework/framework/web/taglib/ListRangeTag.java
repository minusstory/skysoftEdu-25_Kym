/**
 * 0. Project		: hrdkorea
 *
 * 1. FileName		: ListRangeTag.java
 * 2. Package		: kr.co.skysoft.pageNavi
 * 3. Author		: soundb@skysoft.co.kr
 * 4. Creation Date	: 2009. 5. 20.
 * 5. Comment		:
 * 6. History		:
 *                    Name	: Date          : Reason	: Type
 *                   ------------------------------------------------------
 *                   : 2009. 5. 20.  :			: CREATE
 */
package egovframework.framework.web.taglib;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;

/**
 *
 * <PRE>
 * 1. ClassName		: ListRangeTag
 * 2. FileName		: ListRangeTag.java
 * 3. Package		: kr.co.skysoft.pageNavi
 * 4. Author		: soundb@skysoft.co.kr
 * 5. Creation Date	: 2009. 5. 20.
 * 6. Comment		: 
 * </PRE>
 */
public class ListRangeTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= -2283855262534424703L;

	public int doAfterBody() throws JspTagException
	{
		PageNavigationTag	parent	= (PageNavigationTag) findAncestorWithClass(this, PageNavigationTag.class);

		if (parent == null) throw new JspTagException("listRange - PageNavigation error");
		int	listRange	= Integer.parseInt(getBodyContent().getString().trim());
		parent.setListRange(listRange);

		return SKIP_BODY;
	}
}