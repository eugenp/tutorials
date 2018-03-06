<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>编辑分类</title>
    
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
  	<form action="CategoryServlet?oper=edit" method="post">
  	<h3>编辑分类</h3>
   	<table>
   		<tr>
   			<td>名字：</td>
   			<td><input type="text" name="name" size="20" value="${category.name }"></td>
   		</tr>
   		<tr>
   			<td height="50" >描述：</td>
   			<td height="100">
   			<textarea name="description" rows="8" cols="40" >${category.description }</textarea>
   			<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
   			</td>
   		</tr>
   		<tr>
   			<td>显示顺序：</td>
   			<td><input type="text" name="displayOrder"  value="${category.displayOrder }"></td>
   		</tr>
   		<tr>
   			
   			<td>
   			<!-- 要将Id传入后台 -->
   			<input type="hidden" name="id" value="${ category.id}">
   			<input type="hidden" name="blogId" value="${category.blogId }">
   			<input type="submit" value="保存修改">
   			</td>
   		</tr>
   	
   	</table>
   	</form>
   	</fieldset>
  </body>
</html>
