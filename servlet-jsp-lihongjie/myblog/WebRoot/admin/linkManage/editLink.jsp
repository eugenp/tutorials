<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改友情链接</title>
    
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
    	<h3>编辑友情链接</h3>
    	<p>
    	<form action="LinkServlet?oper=edit" method="post">
    	<table>
    		<tr>
    			<td>名称：</td>
    			<td><input type="text" size="30" name="name" value="${link.name }"></td>
    			
    		</tr>
    		<tr>
    			<td>URL:</td>
    			<td><input type="text" size="30" name="url" value="${link.url }"></td>
    		</tr>
    		<tr>
    			<td>显示顺序：</td>
    			<td><input type="text" size="10" name="displayOrder" value="${link.displayOrder }"></td>
    		</tr>
    		<tr>
    			<!-- 页面中没有和数据库表中 对应的id 和blog_id  这里通过隐藏域 将id 和blog_id传入后台保存 -->
    			
    			<td>
    				<input type="hidden" name="id" value="${link.id }">
    				<input type="hidden" name="blogId" value="${link.blogId }">
    				<input type="submit" value="保存修改">
    			</td>
    		</tr>
    	</table>
    	</form>
    </fieldset>
  </body>
</html>
