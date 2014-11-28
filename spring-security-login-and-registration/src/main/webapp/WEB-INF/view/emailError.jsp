<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1 class="alert alert-error">
				<spring:message code="message.email.config.error"></spring:message>
			</h1>
			
		</div>
	</div>
</body>

</html>
