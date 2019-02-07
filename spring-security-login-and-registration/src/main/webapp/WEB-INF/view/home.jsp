<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>

<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1>
				<spring:message code="label.pages.home.message"></spring:message>
			</h1>
			<a href="<c:url value="/j_spring_security_logout" />"><spring:message
					code="label.pages.logout"></spring:message></a>
		</div>
	</div>
</body>

</html>
