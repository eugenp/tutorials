<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'AddUser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	userId:${userId}<br/>
	userName:${username}<br/>
	userPassword:${userpassword }<br/>
	
	<form id="addUser" name="addUser" action="redis/addUser" method="get">
	<input type="submit" value="继续添加">	
	</form>
	
	<form id="queryUser" name="queryUser" action="redis/queryUser">
	<input type="submit" value="查询用户">
	</form>
</body>
</html>
