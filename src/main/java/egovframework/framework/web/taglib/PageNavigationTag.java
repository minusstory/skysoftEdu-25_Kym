package egovframework.framework.web.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <PRE>
 * 1. ClassName		: PageNavigationTag
 * 2. FileName		: PageNavigationTag.java
 * 3. Package		: kr.co.skysoft.pageNavi
 * 4. Author		: soundb@skysoft.co.kr
 * 5. Creation Date	: 2009. 5. 20. 오전 10:20:00
 * 6. Comment		: PageNavigation의 비지니스 로직 클래스
 * </PRE>
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 11. 18. 한재준      맨 처음 페이지 및 맨마지막 페이지 일때 페이징 태그 수정함
 */
@SuppressWarnings("unchecked")
public class PageNavigationTag extends TagSupport {

	private static final long	serialVersionUID	= 1541481931651282533L;

	/**
	 * @param int cpage 			현재 페이지 번호
	 * @param int total 			총 데이터 Row 수
     * @param int listRange 		한 페이지에 표시할 데이터 Row 수
     * @param int pageRange 		한번에 표시할 페이지 번호 수
	 * @param String link 			"<a>"링크 태그
	 * @param String displayType	PageNavigation의 타입코드
	 * @param String naviName		PageNavigation의 프로퍼티화일 경로및 화일명
	 */
	private HashMap	propertyMap	= new HashMap();
    private int cpage;
    private int total;
    private int listRange;
    private int pageRange;
	private String link;
	private String displayType;
	private String naviName;

	public int doStartTag() {
        return EVAL_BODY_INCLUDE;
    }

	/**
	 *
	 * <PRE>
	 * 1. MethodName	: doEndTag
	 * 2. ClassName		: PageNavigationTag
	 * 3. Author		: soundb@skysoft.co.kr
	 * 4. Creation Date	: 2009. 5. 20. 오전 11:20:37
	 * 5. Comment		: doEndTag메서드
	 * </PRE>
	 * 		@return
	 */
    public int doEndTag() {

    	/**
    	 * @param HashMap display 		프로퍼티 값들을 저장
    	 * @param String[] layout 			레이아웃 페턴을 저장
         * @param StringBuffer sbuf 		JSP에 출력할 HTML태그 저장
         * @param HashMap tags 			각각 생성된 태그를 태그별로 저장
    	 * @param String firstTag			제일 첫페이지 버튼 태그 저장
    	 * @param String prevTag			이전 X개 페이지 버튼 태그 저장
    	 * @param String nextTag			다음 X개 페이지 버튼 태그 저장
    	 * @param String noTag				페이지번호 리스트 태그 저장
    	 * @param String lastTag			제일 마지막 페이지 버튼 태그 저장
    	 * @param int totalPage				총 페이지수 저장
    	 * @param int prev					이전 마지막 페이지 번호 저장
    	 * @param int next					다음 첫페이지 번호 저장
    	 */

    	HashMap display=getPageNaviProperties(naviName);
        StringBuffer sbuf = new StringBuffer();

        HashMap tags=new HashMap();
        String firstTag="";
        String prevTag="";
        String nextTag="";
        String noTag="";
        String lastTag="";

        //	전체페이지 수를 구함
        int totalPage = (total-1)/listRange + 1;

        //	이전 마지막 페이지를 구함
        int prev = (int)Math.floor((cpage-1) / pageRange) * pageRange;

        //	다음 첫페이지를 구함
        int next = prev + (pageRange+1);


        //	firstTag setting
        if(displayType.equals("NONLINK") && cpage < (pageRange+1)){
        	firstTag=(String)display.get("first");
        	firstTag=firstTag.replace("#{link}", "#first");
        }else if(displayType.equals("HIDDEN") && cpage < (pageRange+1)) {
        	firstTag="";
        }else{
        	firstTag=(String)display.get("first");
        	firstTag=firstTag.replace("#{link}", link+"\n");
        	firstTag=firstTag.replace("!{pageNo}", "1");
        }
        //tags.put("#\\{first\\}", firstTag);
        // 20111118 modified by jjhan
        // 이부분에서 현재 페이지가 맨처음 페이지이면 넣어 주지 않도록 if 걸어준다.
        if (cpage == 1) tags.put("#\\{first\\}", "");
        else tags.put("#\\{first\\}", firstTag);


        //	prevTag setting
        if(displayType.equals("NONLINK") && cpage < (pageRange+1)){
        	prevTag=(String)display.get("prev");
        	prevTag=prevTag.replace("#{link}", "#prev");
        }else if(displayType.equals("HIDDEN") && cpage < (pageRange+1)) {
        	prevTag="";
        }else if(prev == 0){
        	prevTag=(String)display.get("prev");
        	prevTag=prevTag.replace("#{link}", link+"\n");
        	prevTag=prevTag.replace("!{pageNo}", Integer.toString(cpage));
        }else{
        	prevTag=(String)display.get("prev");
        	prevTag=prevTag.replace("#{link}", link+"\n");
        	prevTag=prevTag.replace("!{pageNo}", Integer.toString(prev));
        }
        //tags.put("#\\{prev\\}", prevTag);
        // 20111118 modified by jjhan
        // 이부분에서 현재 페이지가 맨처음 페이지이면 넣어 주지 않도록 if 걸어준다.
        if (cpage == 1) tags.put("#\\{prev\\}", "");
        else tags.put("#\\{prev\\}", prevTag);


        //	currTag,noTag setting
        for (int i=1+prev; i<next && i<=totalPage; i++ ) {

            if (i==cpage) {

            	noTag=noTag+display.get("currFront")+i+display.get("currRear");

            	if(i < next-1 && i < totalPage){
            		noTag=noTag+display.get("gubun");
            	}

            } else {

            	noTag=noTag+display.get("noFront")+i+display.get("noRear");
            	noTag=noTag.replace("#{link}", link+"\n");
            	noTag=noTag.replace("!{pageNo}", Integer.toString(i));

            	if(i < next-1 && i < totalPage){
            		noTag=noTag+display.get("gubun");
            	}

            }

        }
        tags.put("#\\{list\\}", noTag);


        //	nextTag setting
        if(displayType.equals("NONLINK") && totalPage < next) {
        	nextTag=(String)display.get("next");
        	nextTag=nextTag.replace("#{link}", "#next");
        }else if(displayType.equals("HIDDEN") && totalPage < next) {
        	nextTag="";
        }else if(totalPage < next){
        	nextTag=(String)display.get("next");
        	nextTag=nextTag.replace("#{link}", link+"\n");
        	nextTag=nextTag.replace("!{pageNo}", Integer.toString(cpage));
        }else{
        	nextTag=(String)display.get("next");
        	nextTag=nextTag.replace("#{link}", link+"\n");
        	nextTag=nextTag.replace("!{pageNo}", Integer.toString(next));
        }
        //tags.put("#\\{next\\}", nextTag);
        // 20111118 modified by jjhan
        // 이부분에서 현재 페이지가 맨마지막  페이지이면 넣어 주지 않도록 if 걸어준다.
        if (cpage == totalPage) tags.put("#\\{next\\}", "");
        else tags.put("#\\{next\\}", nextTag);


        //	lastTag setting
        if(displayType.equals("NONLINK") && totalPage < next) {
        	lastTag=(String)display.get("last");
        	lastTag=lastTag.replace("#{link}", "#last");
        }else if(displayType.equals("HIDDEN") && totalPage < next) {
        	lastTag="";
        }else{
        	lastTag=(String)display.get("last");
        	lastTag=lastTag.replace("#{link}", link+"\n");
        	lastTag=lastTag.replace("!{pageNo}", Integer.toString(totalPage));
        }
        //tags.put("#\\{last\\}", lastTag);
        // 20111118 modified by jjhan
        // 이부분에서 현재 페이지가 맨마지막  페이지이면 넣어 주지 않도록 if 걸어준다.
        if (cpage == totalPage) tags.put("#\\{last\\}", "");
        else tags.put("#\\{last\\}", lastTag);

        // layout 구성
		Iterator	tagsIterator	= tags.keySet().iterator();
    	String		layout			= display.get("layout").toString();
		while (tagsIterator.hasNext())
		{
			String	key		= tagsIterator.next().toString();
	    	Pattern	pattern	= Pattern.compile(key);
			Matcher	matcher	= pattern.matcher(layout);
			if (matcher.find()) layout	= matcher.replaceAll(tags.get(key).toString());
		}
    	sbuf.append(layout);

        // JSP 출력
        JspWriter out = pageContext.getOut();

        try {
        	out.print(sbuf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

	/**
	 *
	 * <PRE>
	 * 1. MethodName	: getPageNaviProperties
	 * 2. ClassName		: PageNavigationTag
	 * 3. Author		: soundb@skysoft.co.kr
	 * 4. Creation Date	: 2009. 5. 20. 오전 11:23:21
	 * 5. Comment		: Properties파일을 읽어옴
	 * </PRE>
	 * 		@return HashMap
	 * 		@param naviName
	 * 		@return naviParam
	 */
	private HashMap getPageNaviProperties(String naviName)
	{
		HashMap naviParam = null;

		Object	naviPropertyObj	= propertyMap.get(naviName);
		if (naviPropertyObj != null) naviParam	= (HashMap)naviPropertyObj;
		else
		{
			setPageNaviProperties(naviName);
			naviParam	= getPageNaviProperties(naviName);
		}

		return naviParam;
	}

	private void setPageNaviProperties(String naviName)
	{
		final String PROPERTIES_FILE = naviName;
		HashMap	naviParam	= new HashMap();

		//	파일명에서 subName을 구함
		String[]	splitSubName	= naviName.split("/");
		String		subName			= "app.pagenavi.default";
		Properties	props			= new Properties();
		try
		{
	    	props.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    	naviParam.put("first", props.getProperty(subName+".first"));
    	naviParam.put("prev", props.getProperty(subName+".prev"));

    	// 현재 페이지 번호 프로퍼티 값에서 태그 추출
    	String[] curr=props.getProperty(subName+".curr").trim().toString().split("curr");
    	if(curr.length > 0)
    	{
    		naviParam.put("currFront", curr[0]);
    		naviParam.put("currRear", curr[1]);
    	}
    	else
    	{
    		naviParam.put("currFront", "");
    		naviParam.put("currRear", "");
    	}

    	// 현재 페이지 이외 번호 프로퍼티 값에서  태그 추출
    	String[] no=props.getProperty(subName+".no").trim().toString().split("no");
    	if(no.length > 0)
    	{
    		naviParam.put("noFront", no[0]);
    		naviParam.put("noRear", no[1]);
    	}
    	else
    	{
    		naviParam.put("noFront", "");
    		naviParam.put("noRear", "");
    	}

    	// 번호 구분자 프로퍼티값에서 "을 제거
    	String[] gubun=props.getProperty(subName+".gubun").trim().toString().split("\"");
    	naviParam.put("gubun", gubun[1]);
    	naviParam.put("next", props.getProperty(subName+".next"));
    	naviParam.put("last", props.getProperty(subName+".last"));
    	naviParam.put("layout", props.getProperty(subName+".layout"));

		propertyMap.put(naviName, naviParam);
	}

	public void setCpage(int cpage) {
        this.cpage = cpage;
    }

    public void setListRange(int listRange) {
        this.listRange = listRange;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setLink(String link) {
        this.link = link;
    }

	public void setPageRange(int pageRange)
	{
		this.pageRange = pageRange;
	}

	public void setDisplayType(String displayType)
	{
		this.displayType = displayType;
	}

	public void setNaviName(String naviName)
	{
		this.naviName = "egovframework/resources/properties/app.pagenavi.properties";
	}
}