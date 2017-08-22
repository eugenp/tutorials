<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>
<head>
    <title>Spring Secured Sockets</title>
    <link href="<c:url value="/resources/styles/style.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/angular/angular.min.js" />"></script>

    <script src="<c:url value="/resources/scripts/app.js" />"></script>
    <script src="<c:url value="/resources/scripts/factory.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers.js" />"></script>
</head>
<body>
<c:set var="context" scope="session" value="${pageContext.request.contextPath}"/>
<h1>Welcome!</h1>
<br/>
<div class="requestBodyWrapper" ng-controller="requestBody">
    <h1>Request Body Controller POST Example</h1>
    <form>
        <table>
            <tr>
                <td>User:</td>
                <td><input id="requestBodyUserField" type="text" name="username"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input id="requestBodyPasswordField" type="password" name="password"/></td>
            </tr>
            <tr>
                <td>
                    <button ng-click='postForm("${context}")'>Submit</button>
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="responseBodyWrapper" ng-controller="responseBody">
    <h1>Response Body Controller POST Example</h1>
    <form>
        <table>
            <tr>
                <td>User:</td>
                <td><input id="responseBodyUserField" type="text" name="username"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input id="responseBodyPasswordField" type="password" name="password"/></td>
            </tr>
            <tr>
                <td>
                    <button ng-click='postForm("${context}")'>Submit</button>
                </td>
            </tr>
        </table>
    </form>
    <h3>{response}</h3>
</div>
</body>
</html>