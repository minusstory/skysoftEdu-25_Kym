<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<table border="1" summary="이 표는 번호, 제목, 등록일 항목에 대한 정보를 제공합니다. 제목클릭시 상세페이지로 이동합니다." class="search_list">
<caption>테스트과제 목록</caption>
<colgroup>
	<col width="15%" />
	<col width="*" />
	<col width="20%" />
</colgroup>
<thead>
<tr>
	<th scope="col">번호</th>
	<th scope="col">제목</th>
	<th scope="col">등록일</th>
</tr>
</thead>
<tbody>
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>등록일</td>
	</tr>
<!-- 	<tr><td colspan="3">검색된 결과가 없습니다</td></tr> -->
</tbody>
</table>

<!-- 페이징 처리 -->
<div class="paging">
</div>
 <!-- 페이징 처리 -->

<div class="btn">
	<input type="button" value="등록" onclick="javascript:document.location.href='/test/insert.do'">
</div>