package egovframework.framework.web.savedtoken;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**-----------------------------------------------------------------------
 * skysoft edu Project
 *------------------------------------------------------------------------
 * @Class ValidationToken.java
 * @Description 토큰 검증 대상 여부를 설정하는 Annotation
 * @author 김명철
 * @since 2011. 7. 7.
 * @version 1.0
 * 
 * @Copyright (c) 2011 한국고용정보원, LG CNS 컨소시엄 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------   
 * 수정일              수정자              수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 7. 7. 김명철              최초생성
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationToken {
	
	/**
	 * 다중 토큰 처리시 slot 번호
	 */
	String slot() default "";
}
