<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<title>Home</title>
</head>
<body>
	<h1 class="alert alert-error">
		<spring:message code="message.sessionExpired"></spring:message>
	</h1>
</body>

</html>
