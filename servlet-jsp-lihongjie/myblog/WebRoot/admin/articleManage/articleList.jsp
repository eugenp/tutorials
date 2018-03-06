<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--时间格式化标签库  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
String path = request.getContextPath();
pageContext.setAttribute("ctx", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文章列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
	<script type="text/javascript" src="tablecloth/tablecloth.js">
	<script type="text/javascript">
		function del() {
			var msg = "你真的确定要删除吗？\n\n请确认!";
			if(confirm(msg) == true) {
				return true;
			}else {
				return false;
			}
		}
	</script>
  </head>
  
  <body>
 	<fieldset>
 		<h4>文章列表</h4>
 		<p>
 		<table cellspacing="0" cellpadding="0">
 			<tr bgcolor="gray" align="center">
 				<th align="center">编号</th>
 				<th>主题</th>
 				<th>点击数</th>
 				<th>分类</th>
 				<th>添加时间</th>
 				<th>允许评论</th>
 				<th>状态</th>
 				<th>操作</th>
 				
 			</tr>
 			<c:forEach items="${list }" var="e">
 			<tr align="center">
 				<td>${e.id }</td>
 				<td>${e.title }</td>
 				<td>${e.hits }</td>
 				<td>${e.category.name}</td>
 				<td><fmt:formatDate value="${e.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
 				<!-- 显示那张图片，进行逻辑判断 -->
 				<td>
 					<c:if test="${e.allowComment == 1}">
 					<img src="admin/images/checkmark.png" width="30" alt="是">
 					</c:if>
 					<c:if test="${e.allowComment == 0 }">
 					<img src="admin/images/cross_48.png" width="20" alt="否">
 					</c:if>
 				</td>
 				<td>
 					<c:if test="${e.status == 1}">
 					<img src="admin/images/checkmark.png" width="30" alt="是">
 					</c:if>
 					<c:if test="${e.status == 0 }">
 					<img src="admin/images/cross_48.png" width="20" alt="否">
 					</c:if>
 				</td>
 				
 				<td><a href="${ctx }/EntryServlet?oper=edit">修改</a> &nbsp;<a href="${ctx}/EntryServlet?oper=del&id=${c.id}" onclick="javascript: del()">删除</a></td>
 			</tr>
 			</c:forEach>
 		</table>
 	</fieldset>
  </body>
</html>
