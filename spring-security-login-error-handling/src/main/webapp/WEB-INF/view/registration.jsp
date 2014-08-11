<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<c:if test="${param.regSucc == 1}">
		<div id="error">
			<spring:message code="message.regSucc"></spring:message>
		</div>
</c:if>
<c:if test="${param.regError == 1}">

	<div id="error">
		<spring:message code="message.regError"></spring:message>
		<a href="<c:url value="/login" />"><spring:message code="label.login"></spring:message></a>
	</div>

	
</c:if>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Registration</title>
</head>
<body>
	<H1>This is the registration page</H1>
	<form:form modelAttribute="user" method="POST" enctype="utf8" role="form" >
		<form:errors path="lastError" cssClass="fieldError"/>
		<br>
		<tr>
			<td><label>First Name:</label></td>
			<td><form:input path="firstName" value =""/></td>
			<form:errors path="firstName" class="errors" />
		</tr>
		<tr>
			<td><label>Last Name:</label></td>
			<td><form:input path="lastName" value =""/></td>
			<form:errors path="lastName" class="errors" />
		</tr>
		<tr>
			<td><label>Username (your e-mail address):</label></td>
			<td><form:input path="username" value=""/></td>
			<form:errors path="username"  class="errors" />
		</tr>
		<tr>
			<td><label>Password:</label></td>
			<td><form:input path="password" value=""/></td>
			<form:errors path="password" class="errors" />
		</tr>
		<input type="hidden" name="role" value="1" />
		<button type="submit">submit</button>
	</form:form>
</body>
</html>