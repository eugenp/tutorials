<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="true"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<title><spring:message code="label.pages.home.title"></spring:message></title>
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
    
            <sec:authorize access="hasRole('READ_PRIVILEGE')">
                <spring:message code="label.pages.user.message"></spring:message>
                <br />
            </sec:authorize>

            <sec:authorize access="hasRole('WRITE_PRIVILEGE')">
                <spring:message code="label.pages.admin.message"></spring:message>
                <br />
            </sec:authorize>
             ${param.user}
            <a class="btn btn-default" href="<c:url value="/admin.html" />"><spring:message code="label.pages.admin"></spring:message></a>
    </div>
</body>
</html>