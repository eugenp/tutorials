<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>

    <head>
        <title>Spring Secured Sockets</title>
        <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/styles/login.css"/>" rel="stylesheet">

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

    <body>
        <c:set var="context" scope="session" value="${pageContext.request.contextPath}"/>
        <main>
            <div class="wrapper">
                <!-- Must match the endpoint specified in security config -->
                <h1>JSP Login Form</h1>
                <form name='f' action="authenticate" method='POST'>
                    <table>
                        <tr>
                            <td>User:</td>
                            <td><input type="text" name="username" placeholder="k everyperson"/></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" name="password" placeholder="1234abcd"/></td>
                        </tr>
                        <tr>
                            <td><input name="submit" type="submit" value="submit"/></td>
                        </tr>
                    </table>
                </form>

            </div>
        </main>
        <!-- CSRF Token -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </body>

</html>