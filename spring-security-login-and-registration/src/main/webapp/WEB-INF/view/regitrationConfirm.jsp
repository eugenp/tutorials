<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>
<c:if test="${param.token != null}">
<spring:message code="token.message"><c:out value="${param.token}"></c:out></spring:message>
</c:if>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>

			<spring:message code="message.regSucc"></spring:message>
			<a href="<c:url value="login.html" />"><spring:message code="label.login"></spring:message></a>
</body>
</html>