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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
<div>
            <h1 class="alert alert-info"><spring:message code="message.regSucc"></spring:message></h1>
            <a class="btn btn-primary" href="<c:url value="login.html" />"><spring:message code="label.login"></spring:message></a>
</div>          
</body>
</html>