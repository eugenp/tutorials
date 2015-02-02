<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="message.resetPassword"></spring:message></title>
</head>
<body>
	<div class="container">
		<div class="span12">
			<H1>
				<spring:message code="message.resetPassword"></spring:message>
			</H1>
			<form:form action="user/resetPassword" method="POST" enctype="utf8">
				<br>
				
				<tr>
					<td><label><spring:message code="label.user.email"></spring:message></label></td>
					<td><input name="email" type="email" value="" /></td>
				</tr>
				
				<button type="submit">
					<spring:message code="message.resetPassword"></spring:message>
				</button>
			</form:form>
			<br> <a href="<c:url value="registration.html" />"><spring:message
					code="label.form.loginSignUp"></spring:message></a>
		</div>
	</div>	
</body>

</html>