<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Registration</title>
</head>

<body>
	<H1>This is the registration page</H1>
	<form:form modelAttribute="user" method="POST" enctype="utf8" role="form">
		<br>
		<tr>
			<td><label>First Name:</label></td>
			<td><form:input path="firstName" value="" /></td>
			<form:errors path="firstName" cssClass="alert alert-error" element="div" />
		</tr>
		<tr>
			<td><label>Last Name:</label></td>
			<td><form:input path="lastName" value="" /></td>
			<form:errors path="lastName" cssClass="alert alert-error" element="div" />
		</tr>
		<tr>
			<td><label>Username (your e-mail address):</label></td>
			<td><form:input path="username" value="" /></td>
			<form:errors path="username" cssClass="alert alert-error" element="div" />
		</tr>
		<tr>
			<td><label>Password:</label></td>
			<td><form:input path="password" value="" type="password" /></td>
			<form:errors path="password" cssClass="alert alert-error" element="div" />
		</tr>
		<input type="hidden" name="role" value="1" />
		<button type="submit">submit</button>
	</form:form>
	<br>
	<a href="<c:url value="login.html" />">Back to Login</a>
</body>

</html>