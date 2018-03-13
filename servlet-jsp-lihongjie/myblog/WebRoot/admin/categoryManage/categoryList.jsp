<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%	
	String path = request.getContextPath();
	pageContext.setAttribute("ctx", path);//路径
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>分类列表</title>
   
	<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
	<script type="text/javascript" src="tablecloth/tablecloth.js"></script>
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
	<style type="text/css">
	.paging a{
	text-decoration:none;
	border:1px solid #96c2f1;
	font-size:12px;
	line-height:24px;
	padding-bottom:2px;
	padding-left:5px;
	padding-right:5px;
	padding-top:2px;
	}
	a:link {color:}
	a:visited{color:}
	a:hover{
	color:black;
	text-decoration: underline;
	}
	a:active{
	color:red;
	text-decoration: underline;
	}
	</style>
  </head>
  
  <body>
    <fieldset>
    	<h2>分类列表</h2>
    	<p>
    	<table cellspacing="0" cellpadding="0">
    		<tr>
    			<th>编号id</th>
    			<th>名字</th>
    			<th>描述</th>
    			<th>显示顺序</th>
    			<th>操作</th>
    		</tr>
    		<c:forEach items="${pagination.pageList }" var="c">
    		<tr>
    			<td>${c.id}</td>
    			<td>${c.name }</td>
    			<td>${c.description }</td>
    			<td>${c.displayOrder }</td>
    			<td>
    				<a href="${ctx }/CategoryServlet?oper=findById&id=${c.id}">编辑</a>
    				<a href="${ctx }/CategoryServlet?oper=del&id=${c.id}" onclick="javascript:del()">删除</a>
				</td>
    		</tr>
    		</c:forEach>
    		
    	</table>
    	<!-- 分页操作 -->
    	<div class="paging">
    	    	
    	    	<pg:pager id="pagination" url="${ctx }/CategoryServlet" items="${pagination.maxElements}" maxPageItems="${pagination.pageSize}" maxIndexPages="12">
    	    		<pg:index>
    	    			<pg:first><a href="${pageUrl }&pageNumber=${pageNumber}&oper=listByPage">首页</a></pg:first>
    	    			<pg:prev><a href="${pageUrl }&pageNumber=${pageNumber}&oper=listByPage">上一页</a></pg:prev>
    	    			<pg:pages>
    	    				<a href="${pageUrl}&pageNumber=${pageNumber}&oper=listByPage" >${pageNumber }</a>
    	    			</pg:pages>
    	    			<pg:next><a href="${pageUrl }&pageNumber=${pageNumber}&oper=listByPage">下一页</a></pg:next>
    	    			<pg:last><a href="${pageUrl }&pageNumber=${pageNumber}&oper=listByPage">尾页</a></pg:last>
    	    		</pg:index>
    	    	</pg:pager>
    	    	<!--  
    	    	首页
    	    	<a href="#">上页</a>
    	    	<a href="#">末页</a>
    	    		<span>
    	    			<input type="text" value="1" name="" style="width:60px";font-size="12px" maxlength="20">
    	    			/9
    	    		</span>
    	    		<a href="#"><img src="<%=basePath%>/admin/images/zhuanye.png" height="20"></a>
    	    		
    	    	-->
    	</div>
    </fieldset>
  </body>
</html>
