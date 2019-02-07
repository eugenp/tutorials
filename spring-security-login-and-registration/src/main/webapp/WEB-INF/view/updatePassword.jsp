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
<title><spring:message code="message.updatePassword"></spring:message></title>
</head>
<body>
<sec:authorize access="hasRole('READ_PRIVILEGE')">
	<div class="container">
		<div class="span12">
			<H1>
				<spring:message code="message.updatePassword"></spring:message>
			</H1>
			<form:form action="user/savePassword" method="POST" enctype="utf8">
				<br>
				
				<tr>
					<td><label><spring:message code="label.user.password"></spring:message></label></td>
					<td><input name="password" type="password" value="" /></td>
				</tr>
				
				<button type="submit">
					<spring:message code="message.updatePassword"></spring:message>
				</button>
			</form:form>
			
		</div>
	</div>
	</sec:authorize>
	
</body>

</html>