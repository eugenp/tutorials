ﾌ<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加友情链接</title>
    
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
    	<h3>添加友情链接</h3>
    	<p>
    	<form action="LinkServlet?oper=add" method="post">
    	<table>
    		<tr>
    			<td>名称：</td>
    			<td><input type="text" size="30" name="name"></td>
    			
    		</tr>
    		<tr>
    			<td>URL:</td>
    			<td><input type="text" size="30" name="url"></td>
    		</tr>
    		<tr>
    			<td>显示顺序：</td>
    			<td><input type="text" size="10" name="displayOrder"></td>
    		</tr>
    		<tr>
    			<td><input type="submit" value="添加"></td>
    		</tr>
    	</table>
    	</form>
    </fieldset>
  </body>
</html>
