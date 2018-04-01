<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>评论列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
	<script type="text/javascript" src="tablecloth/tablecloth.js"></script>
  </head>
  
  <body>
	<fieldset>
		<h2>评论列表</h2>
		<table >
			<tr>
				<th><input type="checkbox"></th>
				<th>编号id</th>
				<th>作者</th>
				<th>IP</th>
				<th>添加时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${list }" var="C">
			<tr>
				<td><input type="checkbox"></td>
				<td>${C.id }</td>
				<td>${C.author}</td>
				<td>${C.ip }</td>
				<td>${C.creatTime }</td>
				<td>${C.status }</td>
				<td>
				<input type="submit" value="删除选中项">
				</td>
			</tr>
				
				

			</c:forEach>
		</table>
	</fieldset>
  </body>
</html>
