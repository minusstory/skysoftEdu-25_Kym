<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="egovframework.framework.servlet.view.AlertMessageView" %>
<%@ page import="egovframework.framework.util.StringUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/framework/keis.import.js' />" charset='utf-8'></script>

<jsp:scriptlet>
pageContext.setAttribute("crlf", "\r\n");
pageContext.setAttribute("lf", "\n");
pageContext.setAttribute("cr", "\r");
</jsp:scriptlet>
</head>
<body>
<form name="frm" method="post" action="<c:url value="${redirectUrl}" />">
<c:forEach var="it" items="${param}"><input name="<c:out value='${it.key}'/>" type="hidden" value="<c:out value='${it.value}'/>">
</c:forEach>
<input type="submit" style="display:none" />
</form>
</body>
</html>
<script type="text/javaScript" language="javascript">
//<![CDATA[

var msgStr = '<c:out value='${fn:replace(fn:replace(alertMessageText,crlf,"<br />"),lf,"<br />")}' escapeXml="false" />';
msgStr =  msgStr.replace(/<br \/([^>]*)>/ig,"\n");

<c:choose>
<c:when test="${alertMessageType == 'alertAndRedirect'}">
    alert(msgStr);
	location.href = '<c:url value="${redirectUrl}" />';
</c:when>
<c:when test="${alertMessageType == 'confirmAndRedirect'}">
	var rtn = confirm(msgStr);
	if (rtn) location.href = '<c:url value="${redirectUrl}" />';
	else history.back(-1);
</c:when>
<c:when test="${alertMessageType == 'alertAndBack'}">
    alert(msgStr);
	history.back(-1);
</c:when>
<c:when test="${alertMessageType == 'alertAndForward'}">
    alert(msgStr);
	document.frm.submit();
</c:when>
<c:when test="${alertMessageType == 'closePopupAndLoadOpener'}">
    alert(msgStr);
	opener.location.href = '<c:url value="${redirectUrl}" />';
	window.self.close();
</c:when>
<c:when test="${alertMessageType == 'alertAndOpenerReload'}">
	alert(msgStr);
	opener.location.reload();
</c:when>
<c:when test="${alertMessageType == 'alertAndClosePopup'}">
    if (msgStr !='') alert(msgStr);
	window.self.close();
</c:when>
<%-- 아래 Layer 어 에서 호출 하는 경우 추가 함 --%>
<c:when test="${alertMessageType == 'alertAndRedirectLayer'}">
    alert(msgStr);
	parent.location.href = '<c:url value="${redirectUrl}" />';
</c:when>
<c:when test="${alertMessageType == 'alertAndBackLayer'}">
    alert(msgStr);
	history.back(-1);
</c:when>
<c:when test="${alertMessageType == 'closePopupAndLoadOpenerLayer'}">
	var win	= window;
	latte.interval(100, function () {
		parent.location.href = '<c:url value="${redirectUrl}" />';
		if (win.closePopup) {
		    alert(msgStr);
			win.closePopup();
			return true;
		}
	});
</c:when>
<c:when test="${alertMessageType == 'alertAndClosePopupLayer'}">
	var win	= window;
	latte.interval(100, function () {
		if (win.closePopup) {
		    alert(msgStr);
			win.closePopup();
			return true;
		}
	});
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
//]]>
</script>