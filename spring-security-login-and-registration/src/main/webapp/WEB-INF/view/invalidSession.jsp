<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>
<body>
    <div class="container">
            <h1 class="alert alert-danger">
                <spring:message code="message.sessionExpired"></spring:message>
            </h1>
            <a class="btn btn-primary" href="<c:url value="login.html" />"><spring:message
                    code="label.form.loginLink"></spring:message></a>
    </div>
</body>

</html>