<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.println(basePath);
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value="/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript">
	var getting = {
		url : 'SimpleAjaxPolling',
		dataType : 'json',
		success : function(res) {
			$("p").text(res);
		}
	};
	//关键在这里，Ajax定时访问服务端，不断获取数据 ，这里是1秒请求一次。
	window.setInterval(function() {
		$.ajax(getting)
	}, 10000);
</script>
</head>
<body>
	<p>begin</p>
</body>
</html>