<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
</head>
<body>
	<h1>This is the landing page for the admin</h1>
	<sec:authorize access="hasRole('ROLE_USER')">
		This text is only visible to a user
		<br />
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		This text is only visible to an admin
		<br />
	</sec:authorize>
	<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
	<a href="<c:url value="/admin.html" />">Administrator Page</a>
</body>

</html>