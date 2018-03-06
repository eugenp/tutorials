<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改用户密码页面</title>
    
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
   <h3>修改用户密码</h3>
   <p>
   	<table>
   		<tr>
   			<td>用户：</td>
   			<td><input type="text" ></td>
   		</tr>
   		<tr>
   			<td>旧密码：</td>
   			<td><input type="text"></td>
   		</tr>
   		<tr>
   			<td>新密码：</td>
   			<td><input type="text"></td>
   		</tr>
   		<tr>
   			<td>确认密码：</td>
   			<td><input type="text"></td>
   		</tr>
   		<tr>
   			<td><input type="submit" value="提交"></td>
   		</tr>
   	</table>
   </fieldset>
  </body>
</html>
