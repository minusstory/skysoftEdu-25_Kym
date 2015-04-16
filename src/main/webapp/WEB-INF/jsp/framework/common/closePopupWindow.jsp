<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<script type="text/javaScript" language="javascript">
alert('<c:out value="${PopCloseMsg}"/>');
window.self.close();
</script>
</head>
<body>
</body>
</html>