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
<title>Foo Datastore</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
<%    
    FooDao fooDao = new DatastoreFooDao();   
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String url = userService.createLoginURL(request.getRequestURI());
    String urllinktext = "Login";
    List<Foo> foos = new ArrayList<Foo>();

    if (user != null) {
        url = userService.createLogoutURL(request.getRequestURI());
        urllinktext = "Logout";
       foos= fooDao.getFoos(user.getUserId());
    }
%>
<div style="width: 100%;">

<div class="line"></div>
<div class="topLine">
<div style="float: left;"></div>
<div style="float: left;" class="headline">Foos</div>
<div style="float: right;"> <a href="<%=url%>"><%=urllinktext %></a>
<%=(user == null ? "" : user.getNickname())%></div>
</div>
	<div style="clear: both;" />
	<%
	    if (user != null) {
	    	
	%>
	You have a total number of
	<%=foos.size()%>Foos.
	<table>
		<tr>
			<th>Name</th>
			<th>Description</th>
		</tr>
		<%
		    for (Foo foo : foos) {
		%>
		<tr>
			<td><%=foo.getName()%></td>
			<td><%=foo.getDescription()%></td>
		</tr>
		<%
		    }
		%>
	</table>
	<%
	    }
	%>
<hr />
<div class="main">
		<div class="headline">Foo Access</div>
<%
    if (user != null) {
%>
<form action="/new" method="post" accept-charset="utf-8">
	<table>
		<tr>
			<td><label for="name">Name</label></td>
			<td><input type="text" name="name" id="name" size="50" /></td>
		</tr>
		<tr>
			<td valign="description"><label for="description">Description</label></td>
			<td><textarea rows="4" cols="50" name="description"
					id="description"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit"
				value="Create" /></td>
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