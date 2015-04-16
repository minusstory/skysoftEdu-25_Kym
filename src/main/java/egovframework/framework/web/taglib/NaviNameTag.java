/**
 * 0. Project		: hrdkorea
 *
 * 1. FileName		: NaviNameTag.java
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
 * 1. ClassName		: NaviNameTag
 * 2. FileName		: NaviNameTag.java
 * 3. Package		: kr.co.skysoft.pageNavi
 * 4. Author		: soundb@skysoft.co.kr
 * 5. Creation Date	: 2009. 5. 20.
 * 6. Comment		: 
 * </PRE>
 */
public class NaviNameTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= 8108936614108890589L;

	public int doAfterBody() throws JspTagException
	{
		PageNavigationTag	parent	= (PageNavigationTag) findAncestorWithClass(this, PageNavigationTag.class);

		if (parent == null) throw new JspTagException("naviNameTag - PageNavigation error");
		String	naviName	= getBodyContent().getString().trim();
		parent.setNaviName(naviName);

		return SKIP_BODY;
	}
}