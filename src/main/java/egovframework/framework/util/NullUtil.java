package egovframework.framework.util;

import java.util.Map;

/**-----------------------------------------------------------------------
 * skysoft edu Project
 *------------------------------------------------------------------------
 * @Class NullUtil.java
 * @Description null 체크를 위한 Util 클래스
 * @author 한재준
 * @since 2011. 7. 7.
 * @version 1.0
 *
 * @Copyright (c) 2011 한국고용정보원, LG CNS 컨소시엄 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 7. 7. 한재준       최초생성
 */
public class NullUtil {

    /**
     * Constructor for LNullUtils.
     */
    private NullUtil() {
        super();
    }

    /**
     * 입력값이 널인지 여부를 검사한다. 단 기본은 ""과 null을 모두 true로 리턴한다. 모든 DB Access메소드는
     * null체크가 필요한 경우에 이것을 이용한다.
     *
     * @param value String
     * @return boolean
     */
    public static boolean isNone(String value) {
        return value == null || value.length() == 0;
    }

    /**
     * 입력값이 널인지 여부를 검사한다. 단 기본은 ""과 null을 모두 false로 리턴한다. 모든 DB Access메소드는
     * null체크가 필요한 경우에 이것을 이용한다.
     *
     * @param value String
     * @return boolean
     */
    public static boolean notNone(String value) {
        return value != null && value.length() > 0;
    }


    /**
     * 입력값이 널인지 여부를 검사한다. 단 기본은 ""과 null을 모두 false로 리턴한다. 모든 DB Access메소드는
     * null체크가 필요한 경우에 이것을 이용한다.
     *
     * @param map Map
     * @param key key
     * @param defaultValue 없을시 기본값
     * @return String value
     */
    public static String getMapString(Map map, String key, String defaultValue) {

    	if(!map.containsKey(key)) return defaultValue;
    	else if(map.get(key) == null || map.get(key).equals("")) return defaultValue;
    	else return (String)map.get(key);
    }
}