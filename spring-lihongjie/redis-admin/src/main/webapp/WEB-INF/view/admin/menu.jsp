<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- left nav  -->
<div class="col-sm-1 col-md-1 sidebar">

	<ul class="nav nav-sidebar">
		<li><a id="menu_a" href="javascript:void(0);">Redis</a></li>
	</ul>
	
	
</div>

<script>
	$(document).ready(function() {
		$("#menu_a").on("click", function() {
			window.location.href = '<%=basePath%>' + '/redis/stringList/' + "${serverName}" + '/' + "${dbIndex}";
		});
	})

</script>
