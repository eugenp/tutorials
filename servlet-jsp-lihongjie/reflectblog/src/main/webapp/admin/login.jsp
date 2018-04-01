<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>博客系统管理员登陆页面</title>
    
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
  <fieldset>
  	<h3>博客系统管理员登陆</h3>
  	<p>
  	<form action="UserServlet" method="post">
   	<table>
   		<tr>	
   			<td>用户：</td>
   			<td>
   				<input type="text" name="username" size="20">
   			</td>
   		</tr>
   		<tr>
   			<td>密码：</td>
   			<td>
   				<input type="password" name="password" size="20">
   			</td>
   		</tr>
   		<tr>
   			<td><input type="submit" value="登陆"></td>
   			<td><input type="reset" value="重置"></td>
   		</tr>
   	</table>
   	</form>
   	</fieldset>
  </body>
</html>
