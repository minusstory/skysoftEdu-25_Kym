package egovframework.framework.resource;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @Class CommonMessageSource.java
 * @Description 메세지 리소스를 가져올때 로케일을 자동으로 인식해서 가져오는 클래스
 * @author
 * @since
 * @version 1.0
 */
public class CommonMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {
    private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    /**
     * getReloadableResourceBundleMessageSource()
     *
     * @param reloadableResourceBundleMessageSource - resource MessageSource
     * @return ReloadableResourceBundleMessageSource
     */
    public void setReloadableResourceBundleMessageSource(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
    	this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
    }

    /**
     * getReloadableResourceBundleMessageSource()
     *
     * @return ReloadableResourceBundleMessageSource
     */
    public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
    	return reloadableResourceBundleMessageSource;
    }

    /**
     * 정의된 메세지 조회
     *
     * @param code 메시지 코드
     * @return String 메시지 내용
     */
    public String getMessage(String code) {
    	return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
    }

    /**
     * 정의된 메세지 조회
     *
     * @param code 메시지 코드
     * @param args 메세지안에 들어가 치환되는 문자값
     * @return String 메시지 내용
     */
    public String getMessage(String code,Object[] args) {
    	return getReloadableResourceBundleMessageSource().getMessage(code, args, Locale.getDefault());
    }

    /**
     * 정의된 메세지 조회
     *
     * @param code 메시지 코드
     * @param args 메세지안에 들어가 치환되는 문자값
     * @param defaultMessage 디펄트 메시지
     * @return String 메시지 내용
     */
    public String getMessage(String code,Object[] args,String defaultMessage) {
    	return getReloadableResourceBundleMessageSource().getMessage(code, args,defaultMessage,Locale.getDefault());
    }
}