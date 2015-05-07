<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand"href="<c:url value="/home.html" />"><spring:message code="label.pages.home.title"></spring:message></a>
    </div> 
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.pages.logout"></spring:message></a> </li>
      </ul>
    </div>
</nav>

    <div class="container">
<c:if test="${param.message != null}">
<div class="alert alert-info">
${param.message}
</div>
</c:if>
            <h1>This is the landing page for the admin</h1>
            <sec:authorize access="hasRole('READ_PRIVILEGE')">
        This text is only visible to a user
        <br />
            </sec:authorize>
            <sec:authorize access="hasRole('WRITE_PRIVILEGE')">
        This text is only visible to an admin
        <br />
            </sec:authorize>
        <a class="btn btn-default" href="<c:url value="/changePassword.html" />"><spring:message code="message.changePassword"></spring:message></a>

        <a class="btn btn-default" href="<c:url value="/admin.html" />"><spring:message code="label.pages.admin"></spring:message></a>
    </div>
</body>

</html>