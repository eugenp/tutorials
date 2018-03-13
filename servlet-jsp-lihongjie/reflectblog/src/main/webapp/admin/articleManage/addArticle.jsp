<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>创建文章</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript">
		window.onload =function() {
		
			CKEDITOR.replace("content");<%-- 这里ckeditor必须大写,用textarea的name属性--%>
		};
	</script>
  </head>
  
  <body>
  <fieldset>
    <h3>添加文章</h3>
    <form action="EntryServlet?oper=add" method="post">
    <table id="tab">
    <tr>
    	<td>主题：</td>
    	<td><input type="text" name="title" size="70" maxlength="" style="border:1px solid #000000" /></td>
    </tr>
    <tr>
    	<td>分类：</td>
    	<td>
    		<select name="categoryId">
    			<c:forEach items="${categoryList }" var="c">
    			<option value="${c.id }">${c.name }</option>
    			</c:forEach>
    		</select>
    	</td>
    </tr>
    <tr>
    	<td>允许评论：</td>
    	<td><input name="allowComment" type="radio" value="" checked="checked"/>是
    	<input name="allowComment" type="radio" value="">否
    	</td>
    </tr>
    <tr>
    	<td>状态：</td>
    	<td><input name="status" type="radio" value="" checked="checked"/>开
		<input name="status" type="radio" value=""/>关  
		</td>
    </tr>
    <tr>
    	<td>内容：</td>
    	<td>
    		<textarea rows="6" cols="50" name="content"></textarea>
    		<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
    	</td>
    </tr>
    <tr>
    	<td><input type="submit" value="添加"></td>
    </tr>
    </table>
    </form>
    
    </fieldset>
  </body>
</html>
