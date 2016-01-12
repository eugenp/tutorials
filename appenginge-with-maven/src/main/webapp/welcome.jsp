<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.baeldung.model.Foo"%>
<%@ page import="com.baeldung.dao.*"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome - Foo Datastore</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
	<%
    UserService userService = UserServiceFactory.getUserService();
	   User user = userService.getCurrentUser();
	   String url = userService.createLoginURL(request.getRequestURI());
	   String urlLinktext = "Login";
	   if (user != null) {
	       url = userService.createLogoutURL(request.getRequestURI());
	       urlLinktext = "Logout";
	   }
	%>
	<div class="line"></div>
	<div class="topLine">
    <div style="float: left;"></div>
    <div style="float: left;" class="headline">Foos</div>
    <div style="float: right;"><a href="<%=url%>"><%=urlLinktext%></a>
		      <%=(user == null ? "" : user.getNickname())%></div>
	</div>
	<%
    if (user != null) {
	%>
	<form action="/getFoos" method="post" accept-charset="utf-8">
		<table>

			<tr>
				<td colspan="2" align="right"><input type="submit"
					value="List Foos" /></td>
			</tr>
		</table>
	</form>
	<%
    } else {
	%>
	Please login with your Google account
	<%
	   }
	%>
</body>
</html>