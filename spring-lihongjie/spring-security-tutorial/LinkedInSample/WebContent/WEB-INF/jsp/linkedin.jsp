<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,mycompany.model.StatusUpdate" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LinkedIn上的状态更新</title>
</head>
<body>
	<div>
		<ul>
		<%
			List<StatusUpdate> updates = (List<StatusUpdate>) request.getAttribute("updates");
			for (int i = 0, n = updates.size(); i < n; i++) {
				StatusUpdate update = updates.get(i);
		%>
			<li><%= update.getUsername() %>：<%= update.getStatus() %></li>
		<%
			}
		%>
		</ul>
	</div>
</body>
</html>