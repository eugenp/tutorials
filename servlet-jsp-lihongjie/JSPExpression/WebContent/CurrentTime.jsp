<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Expressions</title>
</head>
<% String bgClor = request.getParameter("color"); %>
<body>
<h2>JSP Expressions</h2>
<%! private int accessCount = 0; %>
<%! 
private String randomHeading() {
	return "hello";
} 
%>
<%= randomHeading() %>
<ul>
	<li>Current Time : <%= new java.util.Date() %></li>
	<li>Server: <%= application.getServerInfo() %></li>
	<li>Session ID: <%= session.getId() %></li>
	<li>Your hostname: <%= request.getRemoteHost() %></li>
	<li>The <code>testParam</code> form parameter:<%= request.getParameter("testParam") %></li>
	<li><% request.getServletPath(); %></li>
	<jsp:scriptlet>request.getServletPath();</jsp:scriptlet>
</ul>
<%!
 private String str;
%>
</body>
</html>