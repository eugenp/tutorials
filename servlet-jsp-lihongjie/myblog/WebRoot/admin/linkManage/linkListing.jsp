<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
//路径
pageContext.setAttribute("ctx", path);

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>友情链接列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
	<script type="text/javascript" src="tablecloth/tablecloth.js">
	</script>
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
    	<h2>友情链接列表</h2>
    	<p>
    	<table cellspacing="0" cellpadding="0">
    		<tr>
    			<th>编号</th>
    			<th>名称</th>
    			<th>URL</th>
    			<th>显示顺序</th>
    			<th>操作</th>
    	    </tr>
    	    <c:forEach items="${list }" var="L">
    	    <tr>
    			<td>${L.id }</td>
    			<td>${L.name }</td>
    			<td>${L.url }</td>
    			<td>${L.displayOrder }</td>
    			<td>
    				<a href="${ctx}/LinkServlet?oper=findById&id=${L.id}">编辑</a>&nbsp;
    				<a href="${ctx}/LinkServlet?oper=del&id=${L.id}" onclick="javascript:del()">删除</a>
    			</td>
    	    </tr>
    	    </c:forEach>
    	</table>
    	<div>
    	    	共有记录170条
    	    	首页
    	    	<a href="#">上页</a>
    	    	<a href="#">末页</a>
    	    		<span>
    	    			<input type="text" value="1" name="" style="width:60px";font-size="12px" maxlength="20">
    	    			/9
    	    		</span>
    	    		<a href="#"><img src="<%=basePath%>/admin/images/zhuanye.png" height="20"></a>
    	</div>
    </fieldset>
  </body>
</html>
