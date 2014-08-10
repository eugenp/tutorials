<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<sec:authorize ifAnyGranted ="ROLE_USER">
<spring:message code="message.unauth" ></spring:message>
</sec:authorize>
<head></head>


<body>
	
<head></head>
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
<H1> Hello Admin</H1>
</sec:authorize>
   
	<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
	<a href="<c:url value="/home.html" />">Home</a>
</body>


</html>
