<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>

<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
	<div class="container">
		<div class="span12">
			<sec:authorize ifAnyGranted="ROLE_USER">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_ADMIN">
				<H1>
					<spring:message code="label.pages.admin.message"></spring:message>
				</H1>
			</sec:authorize>
			<a href="<c:url value="/j_spring_security_logout" />"><spring:message
					code="label.pages.logout"></spring:message></a> <a
				href="<c:url value="/home.html" />"><spring:message
					code="label.pages.home.title"></spring:message></a>
		</div>
	</div>
</body>
</html>
