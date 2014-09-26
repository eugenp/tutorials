<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1>This is the landing page for the admin</h1>
			<sec:authorize access="hasRole('ROLE_USER')">
		This text is only visible to a user
		<br />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
		This text is only visible to an admin
		<br />
			</sec:authorize>
			<a href="<c:url value="/j_spring_security_logout" />"><spring:message
					code="label.pages.logout"></spring:message></a> <a
				href="<c:url value="/admin.html" />"><spring:message
					code="label.pages.admin"></spring:message></a>
		</div>
	</div>
</body>

</html>