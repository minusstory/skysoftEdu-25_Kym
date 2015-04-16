package egovframework.dev.common.vo;

import java.io.Serializable;

/**-----------------------------------------------------------------------
 * skysoft egov2.5Light Project
 *------------------------------------------------------------------------
 * @Class CommonVO.java
 * @Description  CommonVO
 * @author 조상철
 * @since 2011. 7. 19.
 * @version 1.0
 *
 * @Copyright (c) 2012 하늘연소프트 All rights reserved.
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * 수정일         수정자       수정내용
 * ----------  ---------   -----------------------------------------------
 * 2011. 7. 19. 조상철       최초생성
 */
@SuppressWarnings("serial")
public class CommonVO implements Serializable {


	/** 정렬순서(DESC,ASC) */
	private String sortOrdr = "";

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 페이지갯수 */
	private int pageUnit = 10;

	/** 페이지사이즈 */
	private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 1;

	/** lastIndex */
	private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;

	/** rowNo */
	private int rowNo = 0;


	/**
	 * sortOrdr attribute를 리턴한다.
	 *
	 * @return the sortOrdr
	 */
	public String getSortOrdr() {
		return sortOrdr;
	}

	/**
	 * sortOrdr attribute 값을 설정한다.
	 *
	 * @param sortOrdr
	 *            the sortOrdr to set
	 */
	public void setSortOrdr(String sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	/**
	 * pageIndex attribute를 리턴한다.
	 *
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * pageIndex attribute 값을 설정한다.
	 *
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * pageUnit attribute를 리턴한다.
	 *
	 * @return the pageUnit
	 */
	public int getPageUnit() {
		return pageUnit;
	}

	/**
	 * pageUnit attribute 값을 설정한다.
	 *
	 * @param pageUnit
	 *            the pageUnit to set
	 */
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	/**
	 * pageSize attribute를 리턴한다.
	 *
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * pageSize attribute 값을 설정한다.
	 *
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * firstIndex attribute를 리턴한다.
	 *
	 * @return the firstIndex
	 */
	public int getFirstIndex() {
		return firstIndex;
	}

	/**
	 * firstIndex attribute 값을 설정한다.
	 *
	 * @param firstIndex
	 *            the firstIndex to set
	 */
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	/**
	 * lastIndex attribute를 리턴한다.
	 *
	 * @return the lastIndex
	 */
	public int getLastIndex() {
		return lastIndex;
	}

	/**
	 * lastIndex attribute 값을 설정한다.
	 *
	 * @param lastIndex
	 *            the lastIndex to set
	 */
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
	 * recordCountPerPage attribute를 리턴한다.
	 *
	 * @return the recordCountPerPage
	 */
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	/**
	 * recordCountPerPage attribute 값을 설정한다.
	 *
	 * @param recordCountPerPage
	 *            the recordCountPerPage to set
	 */
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	/**
	 * rowNo attribute를 리턴한다.
	 *
	 * @return the rowNo
	 */
	public int getRowNo() {
		return rowNo;
	}

	/**
	 * rowNo attribute 값을 설정한다.
	 *
	 * @param rowNo
	 *            the rowNo to set
	 */
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
}