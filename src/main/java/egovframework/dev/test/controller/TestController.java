package egovframework.dev.test.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.framework.annotation.PageTitle;


/**-----------------------------------------------------------------------
 * skysoft edu Project
 *------------------------------------------------------------------------
 * @Class TestController.java
 * @Description Test 과제 프로젝트
 * @author anonymous
 * @since yyyy.mm.dd
 * @version 1.0
 *
 * @Copyright (c) (주) 하늘연소프트 개발사업부 개발팀 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일			수정자			수정내용
 * ------------------------------------------------------------------------
 * yyyy.mm.dd	anonymous	최초생성
 */
@Controller
public class TestController {//주석

    /** Log Info */
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 리스트(L)
     * @param TestVO 검색조건정보
     * @param model 화면모델
     * @return Test
     * @throws Exception
     */
    @PageTitle("리스트(L)")
    @RequestMapping(value="/test/list.do")
    public String retrieveList(
            ModelMap model
    		)throws Exception {

        return "test/list";
    }

}