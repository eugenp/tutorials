<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>

    <head>
        <title>Spring Secured Sockets</title>
        <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/styles/index.css"/>" rel="stylesheet">

        <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/vendor/angular/angular.min.js" />"></script>
        <script src="<c:url value="/resources/vendor/angular/angular-route.min.js" />"></script>

        <script src="<c:url value="/resources/scripts/app.js" />"></script>
        <script src="<c:url value="/resources/scripts/services/SocketService.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/indexController.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/socketController.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/successController.js" />"></script>
        <script src="<c:url value="/resources/scripts/routes/router.js" />"></script>
    </head>

    <body ng-controller="indexController">
        <main>
            <div class="wrapper">
                <h1>Welcome!</h1>
                <span>{{greeting}}</span>
                <a href="${pageContext.request.contextPath}/login">Click to login.</a>
            </div>
        </main>
    </body>

</html>