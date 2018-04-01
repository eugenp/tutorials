<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改博客系统设定页面</title>
    
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
   		<h3>修改博客系统设定</h3>
   		<p>
   		<table border="0">
   			<tr>
   				<td>名称：</td>
   				<td><input type="text" value="在线学IT新技术"></td>
   			</tr>
   			<tr>
   				<td height="100" width="150">描述：</td>
   				<td><textarea name="coments" rows="10" cols="45"></textarea></td>
   			</tr>
   			<tr>
   				<td>显示博文数量：</td>
   				<td><input type="text" value="2" size="4"></td>
   			</tr>
   			<tr>
   				<td>最新主题数量：</td>
   				<td><input type="text" value="2" size="4"></td>
   			</tr>
   			<tr>
   				<td>最新评论数量：</td>
   				<td><input type="text" value="2" size="4"></td>
   			<tr>
   				<td>后台显示分页大小：</td>
   				<td><input type="text" value="6" size="4"></td>
   			</tr>
   			<tr>
   				<td>评论需要批准：</td>
   				<td><input type="radio" name="radiobutton1" value="one" checked="checked">是
   					<input type="radio" name="radionbutton1" value="two">否
   				</td>
   			</tr>
   			<tr>
   				<td>状态：</td>
   				<td><input type="radio" name="radiobutton2" value="three" checked="checked">开
   					<input type="radio" name="radionbutton2" value="four">关
   				</td>
   			</tr>
   			<tr>
   				<td><input type="submit" value="更新"></td>
   			</tr>
   		</table>
   	</fieldset>
  </body>
</html>
