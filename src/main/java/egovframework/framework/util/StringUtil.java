package egovframework.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class StringUtil.java
 * @Description 문자열 데이터 처리 관련 Util 클래스
 * @author
 * @since
 * @version 1.0
 */
public class StringUtil {
    /**
     * 빈 문자열 <code>""</code>.
     */
    public static final String EMPTY = "";

    /**
     * <p>Padding을 할 수 있는 최대 수치</p>
     */
    // private static final int PAD_LIMIT = 8192;

    /**
     * <p>An array of <code>String</code>s used for padding.</p>
     * <p>Used for efficient space padding. The length of each String expands as needed.</p>
     */
    /*
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	static {
		// space padding is most common, start with 64 chars
		PADDING[32] = "                                                                ";
	}
     */

    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * @param source 원본 문자열 배열
     * @param output 더할문자열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else
                returnVal = source;
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * @param source 원본 문자열 배열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if (source != null) {
            if (source.length() > slength) {
                result = source.substring(0, slength);
            } else
                result = source;
        }
        return result;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * <p>기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.</p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str  입력받는 기준 문자열
     * @param remove  입력받는 문자열에서 제거할 대상 문자열
     * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>문자열 내부의 콤마 character(,)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeCommaChar(null)       = null
     * StringUtil.removeCommaChar("")         = ""
     * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str 입력받는 기준 문자열
     * @return " , "가 제거된 입력문자열
     *  입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeCommaChar(String str) {
        return remove(str, ',');
    }

    /**
     * <p>문자열 내부의 마이너스 character(-)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeMinusChar(null)       = null
     * StringUtil.removeMinusChar("")         = ""
     * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str  입력받는 기준 문자열
     * @return " - "가 제거된 입력문자열
     *  입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }


    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;

        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
     */
    public static String replaceOnce(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        if (source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            rtnStr.append(preStr).append(object).append(nextStr);
            return rtnStr.toString();
        } else {
            return source;
        }
    }

    /**
     * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
     *
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replaceChar(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;

        char chA;

        for (int i = 0; i < subject.length(); i++) {
            chA = subject.charAt(i);

            if (srcStr.indexOf(chA) >= 0) {
                preStr = srcStr.substring(0, srcStr.indexOf(chA));
                nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
                srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
            }
        }

        return srcStr;
    }

    /**
     * <p><code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.</p>
     *
     * <p>입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.</p>
     *
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str  검색 문자열
     * @param searchStr  검색 대상문자열
     * @return 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }


    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo", "bar")= "foo"
     * StringUtil.decode("", null, "foo", "bar") = "bar"
     * StringUtil.decode(null, "", "foo", "bar") = "bar"
     * StringUtil.decode("하이", "하이", null, "bar") = null
     * StringUtil.decode("하이", "하이  ", "foo", null) = null
     * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
     * StringUtil.decode("하이", "하이  ", "foo", "bar") = "bar"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @param defaultStr sourceStr와 compareStr의 값이 다를 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 defaultStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
        if (sourceStr == null && compareStr == null) {
            return returnStr;
        }

        if (sourceStr == null && compareStr != null) {
            return defaultStr;
        }

        if (sourceStr.trim().equals(compareStr)) {
            return returnStr;
        }

        return defaultStr;
    }

    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo") = "foo"
     * StringUtil.decode("", null, "foo") = ""
     * StringUtil.decode(null, "", "foo") = null
     * StringUtil.decode("하이", "하이", "foo") = "foo"
     * StringUtil.decode("하이", "하이 ", "foo") = "하이"
     * StringUtil.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 sourceStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr) {
        return decode(sourceStr, compareStr, returnStr, sourceStr);
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";

        if (object != null) {
            string = object.toString().trim();
        }

        return string;
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static String nullConvert(Object src) {
	//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
	if (src != null && src instanceof java.math.BigDecimal) {
	    return ((BigDecimal)src).toString();
	}

	if (src == null || src.equals("null")) {
	    return "";
	} else {
	    return ((String)src).trim();
	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static String nullConvert(String src) {

	if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
	    return "";
	} else {
	    return src.trim();
	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;0&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;0&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(Object src) {

	if (src == null || src.equals("null")) {
	    return 0;
	} else {
	    return Integer.parseInt(((String)src).trim());
	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(String src) {

	if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
	    return 0;
	} else {
	    return Integer.parseInt(src.trim());
	}
    }

    /**
     * <p>문자열에서 {@link Character#isWhitespace(char)}에 정의된
     * 모든 공백문자를 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeWhitespace(null)         = null
     * StringUtil.removeWhitespace("")           = ""
     * StringUtil.removeWhitespace("abc")        = "abc"
     * StringUtil.removeWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  공백문자가 제거도어야 할 문자열
     * @return the 공백문자가 제거된 문자열, null이 입력되면 <code>null</code>이 리턴
     */
    public static String removeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }

        return new String(chs, 0, count);
    }

    /**
     * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
     *
     * @param strString
     * @return HTML 태그를 치환한 문자열
     */
    public static String checkHtmlView(String strString) {

    if(strString == null) return "";

	String strNew = "";

	try {
	    StringBuffer strTxt = new StringBuffer("");

	    char chrBuff;
	    int len = strString.length();

	    for (int i = 0; i < len; i++) {
	    chrBuff = (char)strString.charAt(i);

		switch (chrBuff) {
		case '<':
		    strTxt.append("&lt;");
		    break;
		case '>':
		    strTxt.append("&gt;");
		    break;
		case '"':
		    strTxt.append("&quot;");
		    break;
		case 10:
		    strTxt.append("<br>");
		    break;
		case ' ':
		    strTxt.append("&nbsp;");
		    break;
		//case '&' :
		    //strTxt.append("&amp;");
		    //break;
		default:
		    strTxt.append(chrBuff);
		}
	    }

	    strNew = strTxt.toString();

	} catch (Exception ex) {
	    return null;
	}

	return strNew;
    }


    /**
     * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @return result 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator) throws NullPointerException {
        String[] returnVal = null;
        int cnt = 1;

        int index = source.indexOf(separator);
        int index0 = 0;
        while (index >= 0) {
            cnt++;
            index = source.indexOf(separator, index + 1);
        }
        returnVal = new String[cnt];
        cnt = 0;
        index = source.indexOf(separator);
        while (index >= 0) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);

        return returnVal;
    }

    /**
     * <p>{@link String#toLowerCase()}를 이용하여 소문자로 변환한다.</p>
     *
     * <pre>
     * StringUtil.lowerCase(null)  = null
     * StringUtil.lowerCase("")    = ""
     * StringUtil.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str 소문자로 변환되어야 할 문자열
     * @return 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }

    /**
     * <p>{@link String#toUpperCase()}를 이용하여 대문자로 변환한다.</p>
     *
     * <pre>
     * StringUtil.upperCase(null)  = null
     * StringUtil.upperCase("")    = ""
     * StringUtil.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str 대문자로 변환되어야 할 문자열
     * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * <p>입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripStart(null, *)          = null
     * StringUtil.stripStart("", *)            = ""
     * StringUtil.stripStart("abc", "")        = "abc"
     * StringUtil.stripStart("abc", null)      = "abc"
     * StringUtil.stripStart("  abc", null)    = "abc"
     * StringUtil.stripStart("abc  ", null)    = "abc  "
     * StringUtil.stripStart(" abc ", null)    = "abc "
     * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }

        return str.substring(start);
    }


    /**
     * <p>입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripEnd(null, *)          = null
     * StringUtil.stripEnd("", *)            = ""
     * StringUtil.stripEnd("abc", "")        = "abc"
     * StringUtil.stripEnd("abc", null)      = "abc"
     * StringUtil.stripEnd("  abc", null)    = "  abc"
     * StringUtil.stripEnd("abc  ", null)    = "abc"
     * StringUtil.stripEnd(" abc ", null)    = " abc"
     * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }

        return str.substring(0, end);
    }

    /**
     * <p>입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.strip(null, *)          = null
     * StringUtil.strip("", *)            = ""
     * StringUtil.strip("abc", null)      = "abc"
     * StringUtil.strip("  abc", null)    = "abc"
     * StringUtil.strip("abc  ", null)    = "abc"
     * StringUtil.strip(" abc ", null)    = "abc"
     * StringUtil.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String strip(String str, String stripChars) {
	if (isEmpty(str)) {
	    return str;
	}

	String srcStr = str;
	srcStr = stripStart(srcStr, stripChars);

	return stripEnd(srcStr, stripChars);
    }

    /**
     * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @param arraylength 배열 길이
     * @return 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
        String[] returnVal = new String[arraylength];
        int cnt = 0;
        int index0 = 0;
        int index = source.indexOf(separator);
        while (index >= 0 && cnt < (arraylength - 1)) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        if (cnt < (arraylength - 1)) {
            for (int i = cnt + 1; i < arraylength; i++) {
                returnVal[i] = "";
            }
        }

        return returnVal;
    }

    /**
     * 문자열 A에서 Z사이의 랜덤 문자열을 구하는 기능을 제공 시작문자열과 종료문자열 사이의 랜덤 문자열을 구하는 기능
     *
     * @param startChr
     *            - 첫 문자
     * @param endChr
     *            - 마지막문자
     * @return 랜덤문자
     * @exception MyException
     * @see
     */
    public static String getRandomStr(char startChr, char endChr) {

	int randomInt;
	String randomStr = null;

	// 시작문자 및 종료문자를 아스키숫자로 변환한다.
	int startInt = Integer.valueOf(startChr);
	int endInt = Integer.valueOf(endChr);

	// 시작문자열이 종료문자열보가 클경우
	if (startInt > endInt) {
	    throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
	}

	try {
	    // 랜덤 객체 생성
	    SecureRandom rnd = new SecureRandom();

	    do {
		// 시작문자 및 종료문자 중에서 랜덤 숫자를 발생시킨다.
		randomInt = rnd.nextInt(endInt + 1);
	    } while (randomInt < startInt); // 입력받은 문자 'A'(65)보다 작으면 다시 랜덤 숫자 발생.

	    // 랜덤 숫자를 문자로 변환 후 스트링으로 다시 변환
	    randomStr = (char)randomInt + "";
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// 랜덤문자열를 리턴
	return randomStr;
    }

    /**
     * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을
     * 복원하는 기능을 제공함 String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
     * String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서
     * EUC-KR
     *
     * @param srcString
     *            - 문자열
     * @param srcCharsetNm
     *            - 원래 CharsetNm
     * @param charsetNm
     *            - CharsetNm
     * @return 인(디)코딩 문자열
     * @exception MyException
     * @see
     */
    public static String getEncdDcd(String srcString, String srcCharsetNm, String cnvrCharsetNm) {

	String rtnStr = null;

	if (srcString == null)
	    return null;

	try {
	    rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
	} catch (UnsupportedEncodingException e) {
	    rtnStr = null;
	}

	return rtnStr;
    }

/**
     * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다
     * @param 	srcString 		- '<'
     * @return 	변환문자열('<' -> "&lt"
     * @exception MyException
     * @see
     */
    public static String getSpclStrCnvr(String srcString) {

	String rtnStr = null;

	try {
	    StringBuffer strTxt = new StringBuffer("");

	    char chrBuff;
	    int len = srcString.length();

	    for (int i = 0; i < len; i++) {
	    chrBuff = (char)srcString.charAt(i);

		switch (chrBuff) {
		case '<':
		    strTxt.append("&lt;");
		    break;
		case '>':
		    strTxt.append("&gt;");
		    break;
		case '&':
		    strTxt.append("&amp;");
		    break;
		default:
		    strTxt.append(chrBuff);
		}
	    }

	    rtnStr = strTxt.toString();

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return rtnStr;
    }

    /**
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    public static String getTimeStamp() {

	String rtnStr = null;

	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
	String pattern = "yyyyMMddhhmmssSSS";

	try {
	    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
	    Timestamp ts = new Timestamp(System.currentTimeMillis());

	    rtnStr = sdfCurrent.format(ts.getTime());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return rtnStr;
    }

    /**
     * html의 특수문자를 표현하기 위해
     *
     * @param srcString
     * @return String
     * @exception Exception
     * @see
     */
    public static String getHtmlStrCnvr(String srcString) {

    	String tmpString = srcString;

		try
		{
			tmpString = tmpString.replaceAll("&lt;", "<");
			tmpString = tmpString.replaceAll("&gt;", ">");
			tmpString = tmpString.replaceAll("&amp;", "&");
			tmpString = tmpString.replaceAll("&nbsp;", " ");
			tmpString = tmpString.replaceAll("&apos;", "\'");
			tmpString = tmpString.replaceAll("&quot;", "\"");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return  tmpString;

	}

    /**
     * <p>날짜 형식의 문자열 내부에 마이너스 character(-)를 추가한다.</p>
     *
     * <pre>
     *   StringUtil.addMinusChar("20100901") = "2010-09-01"
     * </pre>
     *
     * @param date  입력받는 문자열
     * @return " - "가 추가된 입력문자열
     */
	public static String addMinusChar(String date) {
		if(date.length() == 8)
		    return date.substring(0,4).concat("-").concat(date.substring(4,6)).concat("-").concat(date.substring(6,8));
		else return "";
	}


    /**
     * 문자열에서 특정 패턴을 검색한다.
     *
     * @param pattern정규표현식  text검색할문자열 index그룹 없을시 0
     * @return String
     * @exception Exception
     * @see
     */
	public static String getPatternString(String pattern, String text, int index)
	{

        String result = "";
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher wordMatche= patternCompile.matcher(text);

        if(wordMatche.find() == true)result = wordMatche.group(index);

        return result;
    }


    /**
     *  줄바꿈 문자를 <br /> 태그로 변경한다.
     *
     * @param str 원본 문자열
     * @return String 줄바꿈 문자열
     * @see
     */
    public static String toNl2br(String str) {
        String rtn = "";
        if (str == null) {

        } else {
            rtn = replace(str, "\r\n", "<br />");
            rtn = replace(rtn, "\n", "<br />");
            rtn = replace(rtn, "\r", "<br />");
        }

        return rtn;
    }


    /**
     *  스트링의 바이트 길이를 구한다.
     *
     * @param c 원본 문자열
     * @return int 원본문자열 바이트 길이
     * @see
     */
    public static int getByteLength(String c) {
        String s = new String(c + "");
        byte abyte0[] = s.getBytes();
        return abyte0.length;
    }


    /**
     *  textArea의 Data를 "ENTER" 키를 "br tag"로  변경한다
     *
     * @param str 원본 문자열
     * @return String 변경된 문자열
     * @see
     */

    public  String textAreaToPage(String str) {

		String result = str;

		result = replace(result, "\r\n", "<br/>");
		result = replace(result, "\n",   "<br/>");
		result = replace(result, " ", "&nbsp;");
		result = replace(result, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

		return result;
   }


    /**
     *  문자열의 바이트길이에서 주어진 Max Byte Length 만큼 문자열들을
     *  잘라서 String[] Array에 담아 반환한다.(마지막이 한글일경우 1 Byte작게 반환한다.)
     * @param str 처리대상 문자열
     * @param maxByteLength 제한 길이 Byte 길이
     * @return String[] 지정된 문자열로 바꿔진 String Array
     * @see
     */
    public String[] getByteLengthSplitString(String str, int maxByteLength) {

        int iLength = 0;

        StringBuffer sb = new StringBuffer();
        Vector vRtn = new Vector();

        for(int i = 0 ; i < str.length(); i++){

            int tmpLength = getByteLength(str.substring(i, i+1));
            iLength = iLength + tmpLength;

            if (iLength <= maxByteLength)
            {
                sb.append(str.substring(i, i+1));

            } else {
                if( 2 == tmpLength && 11 == iLength) {
                    iLength = 2;
                } else {
                    iLength = iLength-maxByteLength;
                }

                vRtn.addElement(sb.toString());
                sb.delete(0, sb.length());
                sb.append(str.substring(i, i+1));
            }

        }

        vRtn.addElement(sb.toString());
        return (String[])vRtn.toArray(new String[vRtn.size()]);

    }

    /**
     *  문자열을 입력 받아 시작하는 위치에서 종료 위치 까지 substring 한다
     * @param st  시작위치
     * @param end 종료위치
     * @param target 처리대상 문자열
     * @return String 시작위치에서 종료위치 까지의 문자열
     * @see
     */
    /**
     *
     *
     * @param st
     * @param end
     * @param target
     * @return
     */
    public static String getSubStr(int st,int end,String target) {

        String str = "";
        if(target != null) {
            if(target.length() < end) {
                return str;
            }
            str = target.substring(st, end);
        }
        return str;
    }


    /**
     * 문자열을 입력받아 XSS관련 태그(&<>"\)를 대체문자로 치환한다.
     *
     * @param s 변환대상 문자열
     * @return String 변환된 문자열
     */
    public static String tagOff(String s) {
        if(isEmpty(s)) return "";
        StringBuffer stringbuffer = new StringBuffer();
        char ac[] = s.toCharArray();
        int i = ac.length;
        for(int j = 0; j < i; j++)
            if(ac[j] == '&')
                stringbuffer.append("&amp;");
            else
            if(ac[j] == '<')
                stringbuffer.append("&lt;");
            else
            if(ac[j] == '>')
                stringbuffer.append("&gt;");
            else
            if(ac[j] == '"')
                stringbuffer.append("&quot;");
            else
            if(ac[j] == '\'')
                stringbuffer.append("&#039;");
            /*else                            살펴보고 추가해 주세요..
            if(ac[j] == '\n')
                stringbuffer.append("<br/>");
            else
            if(ac[j] == '\r')
                stringbuffer.append(" ");*/
            else
                stringbuffer.append(ac[j]);

        return stringbuffer.toString();
    }


    /**
     * 오늘 날짜를 년월일시분초 형식으로 리턴한다.
     * @return 오늘 날짜시간
     */
    public static String getCurrentDateTime() {
        return getToday("yyyyMMddHHmmss");
    }

    /**
     * 오늘날짜를 주어진 포맷 문자열 형식으로 리턴한다.
     * @param formatString 포맷문자열
     * @return 오늘 날짜
     */
    public static String getToday(String formatString) {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = formatString;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);

        return formatter.format(today);
    }



    /**
     * 난수를 조회한다.
     * @param maxNumber 난수 개수 (0 ~ maxNumber-1)
     * @return 난수
     */
    public static int getRandomNum(int maxNumber) {
        int result = 0; // 결과값을 저장할 변수
        Random rangen = new Random(); // Random obj 생성
        result = rangen.nextInt() % maxNumber;
        // nextInt()를 이용하여 Random수를 발생시킨다.
        // nextInt()는 random수를 정수로 만들어 준다.
        // 실제로 random값은 0 부터 1까지의 실수가 나온다.
        // % 3의 경우는 0 부터 2까지의 값이 나오게 만든다. 필요에 따라 조정!!
        if (result < 0)
            result = result * -1;
        // 위에서 만든 결과값이 음수의 경우 배열의 Index로 사용할 수 없으므로
        // 양수로 만들어 준다.
        return result; // 결과를 넘긴다.
    }

    /**
     * 만나이를 구한다.
     * @param resno 주민번호
     * @return 만나이
     */
    public static int getAge(String resno) {

        int birthYear = 0;
        int age = 0;

        try {
            if (resno.length()!=13) {
                return 0;
            }
            birthYear = Integer.parseInt(resno.substring(0,2));

            if (birthYear < 20) {
                birthYear += 2000;
            } else {
                birthYear += 1900;
            }

            // 현재의 년을 가져온다.
            Calendar cal = Calendar.getInstance();
            int thisYear = cal.get(Calendar.YEAR);


            String today     = StringUtil.getToday("yyyyMMdd");
            String birthday  = birthYear + resno.substring(2,6);

            if (Integer.parseInt(today) < Integer.parseInt(birthday)){
                age =  thisYear - birthYear -1;
            } else {
               age = thisYear - birthYear;
            }
            return age;

        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * param 을 받아 size 만큼 배열을 만들어 돌려준다.
     * @param param 123
     * @param size  4
     * @return {[1], [2], [3], []}
     */
    public static String[] getSubstrToArray(String param, int size) {
    	String result[] = new String[size];

    	for(int i = 0; i < size; i++) {
    		result[i] = "";
    		if(param.length() >= size) result[i] = param.substring(i, i+1);
    	}

    	return result;
    }

    /**
     * param을 받아 str로 잘라서 배열을 만들어 돌려준다.
     * @param param aa-bb-cc
     * @param str -
     * @return {[aa],[bb],[cc]}
     */
    public static String[] getSplitToArray(String param, String str) {
    	String result[] = null;

    	if(param.indexOf(str) != -1){
    		result = param.split(str);
    	}else{
    		result = new String[1];
    		result[0] = param;
    	}

    	return result;
    }

    /**
     * 코드값을 받아 해당하는 값을 돌려준다 (OA능력)
     * @param param 3  2  1
     * @return      상  중  하  미입력
     */
    public static String getCodeAbilityOA(String param) {
    	String result = "";

    	if(null == param || "".equals(param)) {
			result = "미입력";
		} else if("3".equals(param)) {
			result = "상";
		} else if("2".equals(param)) {
			result = "중";
		} else if("1".equals(param)) {
			result = "하";
		}

    	return result;
    }

  	 public static String decodeUnicode(String unicode){
   	  StringBuffer str = new StringBuffer();

   	  char ch = 0;
   	  for( int i= unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u") ){
   	   ch = (char)Integer.parseInt( unicode.substring( i + 2, i + 6 ) ,16);
   	   str.append( unicode.substring(0, i) );
   	   str.append( String.valueOf(ch) );
   	   unicode = unicode.substring(i + 6);
   	  }
   	  str.append( unicode );

   	  return str.toString();
   	 }


   	 public static String encodeUnicode(String unicode) {
  	      StringBuffer str = new StringBuffer();

  	      for (int i = 0; i < unicode.length(); i++) {
  	       if(((int) unicode.charAt(i) == 32)) {
  	        str.append(" ");
  	        continue;
  	       }
  	       str.append("\\u");
  	     str.append(Integer.toHexString((int) unicode.charAt(i)));
  	       }
  	    return str.toString();
   	 }


     /**
      * 필드 네임을 입력 받아 펑션이름을  만들어준다.
      * @param prefixStr 펑션의 종류 : set 인지 get 인지 값.
      * @param fieldName 클래스의 필드값
      *
      * @return String 펑션명
      */
     public static String getMethodName(String prefixStr,String fieldName) {
        String methodName ="";
 		if (fieldName.length()>1){
			if (fieldName.substring(1,2).equals(fieldName.substring(1,2).toUpperCase())){
				methodName = prefixStr + fieldName.substring(0, 1) +  fieldName.substring(1, 2).toUpperCase() + fieldName.substring(2);
			}else{
				methodName = prefixStr + fieldName.substring(0, 1).toUpperCase() +  fieldName.substring(1);
			}
	}
	else{
		methodName = prefixStr + fieldName.substring(0, 1).toUpperCase() ;
		}
 		  return methodName;
     }
     /**
      * 입력된 값의 미리지정한값 이후로 인코딩한후 최대값만큼만 잘라온다.
      * @param content 가공되어야할 문값.
      * @param maxWidth 잘라내야할 바이트 최대값.
      * @param enc      인코딩.
      * @param suffix      인코딩.
      * @return String - 로그인한 사용자의 reDirect될 URL
      */
     public static String abbreviate(String content, int maxWidth, String enc, String suffix)
     {
         if(content == null)
             return "";
         if(maxWidth < suffix.length())
             throw new IllegalArgumentException();
         int ptr = maxWidth - suffix.length();
         String str = null;
         try
         {
             byte bytes[] = content.getBytes(enc);
             str = new String(bytes, 0, bytes.length >= ptr ? ptr : bytes.length, enc);
         }
         catch(UnsupportedEncodingException e)
         {
             //throw new Exception();
         	return "";
         }
         for(ptr = (ptr = str.length() - 4) >= 0 ? ptr : 0; ptr < str.length() && str.charAt(ptr) == content.charAt(ptr); ptr++);
         return str.substring(0, ptr) + suffix;
     }

     /**
      * 보안을 위해 입력된 값 중에 CrossSiteScripting에 관련된 내용 변환한다. .
      * @param str 가공되어야할 문값.
      * @return String 처리된 문자열 값
      */
 	public static String replaceForSecurity(String str)
	{

 		if (isEmpty(str)) {
 		    return str;
 		}

		//Pattern pattern1 = Pattern.compile("\'|\"|<script>|</script>|<object>|</object>", Pattern.CASE_INSENSITIVE);
		//Pattern pattern1 = Pattern.compile("\'|\"|<(no)?script[^>]*>|</(no)?script[^>]*>|<object[^>]*>|</object[^>]*>|<iframe[^>]*>", Pattern.CASE_INSENSITIVE);
		Pattern pattern1 = Pattern.compile("<(no)?script[^>]*>|</(no)?script[^>]*>|<object[^>]*>|</object[^>]*>|<iframe[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern1.matcher(str);
        StringBuffer sb = new StringBuffer();
        String replaceStr = "";
        while (m.find()) {

        	if(m.group().toLowerCase().equals("\'")) replaceStr = "\'\'";
        	//else if(m.group().toLowerCase().equals("\"")) replaceStr = "\"\"";
        	else replaceStr = "";
        	//System.out.println("Invalid String [" + m.group() + "] was replaced " + "["+ replaceStr +"]");
            m.appendReplacement(sb, replaceStr);
        }
        m.appendTail(sb);


		return sb.toString();
	}

    /**
     * 입력받은 문자열에서 주민등록번호를 추출한다.
     * @param str 가공되어야할 문값.
     * @return String 처리된 문자열 값 (복수일경우 "|"로 구분하여 반환)
     */
	public static String findRRN(String str) {

		String result = "";

		RRN r = new RRN();
		for (int i=0;r.more;i++) {
			str = r.findRRNService(str);
			if (r.rrn != "Not found"){
				result = result + (i > 0 ? "|" : "") + r.rrn;
			}
		}
		return result;
	}
}

class RRN {
	String rrn;
	String PatternStr = "\\d\\d(0[1-9]|1[0-2])([0-2][1-9]|3[0-1])[1-4]\\d{6}";
	Pattern pat = Pattern.compile(PatternStr);

	Matcher mat;
	boolean found;
	boolean more = true;

	public String findRRNService(String str) {
		mat = pat.matcher(str);
		found = mat.find();
		if (found)
		{
			rrn = mat.group();
			more = true;
			str = str.substring(mat.end());
		}
		else
		{
			rrn = "Not found";
			more = false;
		}
		return str;
	}
}
