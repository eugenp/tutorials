<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>

<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	<div id="error">
		<spring:message code="message.logoutError"></spring:message>
	</div>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>

<body>
	<div class="container">
		<div class="span12">
			<c:if test="${param.logSucc == true}">
				<div id="success">
					<spring:message code="message.logoutSucc"></spring:message>
				</div>
			</c:if>
			<a href="<c:url value="login.html" />"><spring:message
					code="label.form.loginLink"></spring:message></a>
		</div>
	</div>
</body>

</html>