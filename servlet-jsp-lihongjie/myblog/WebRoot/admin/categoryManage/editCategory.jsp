<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		window.onload =
		function() {
			CKEDITOR.replace("description");<%-- 这里ckeditor必须大写,用textarea的name属性--%>
		}
	</script>
  </head>
  <body>
  <fieldset>
  	<form action="CategoryServlet?oper=add" method="post">
  	<h3>添加分类</h3>
   	<table>
   		<tr>
   			<td>名字：</td>
   			<td><input type="text" name="name" size="20"></td>
   		</tr>
   		<tr>
   			<td height="50" >描述：</td>
   			<td height="100">
   			<!-- 插件的使用 -->
   			<textarea name="description"></textarea>
   			<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
   			</td>
   		</tr>
   		<tr>
   			<td>显示顺序：</td>
   			<td><input type="text" name="displayOrder" size="2" maxlength="2" style="border:1px solid #000000"></td>
   		</tr>
   		<tr>
   			
   			<td>
   			<input type="submit" value="添加">
   			</td>
   		</tr>
   	
   	</table>
   	</form>
   	</fieldset>
  </body>
</html>
