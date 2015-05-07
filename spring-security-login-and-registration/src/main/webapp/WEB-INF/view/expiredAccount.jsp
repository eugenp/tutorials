<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
<div class="container">
<h1 class="alert alert-info">
  <spring:message code="auth.message.expired"></spring:message> 
</h1>
<br>
<a class="btn btn-default" href="<c:url value="registration.html" />"><spring:message
code="label.form.loginSignUp"></spring:message></a>
</div>
</body>
</html>
