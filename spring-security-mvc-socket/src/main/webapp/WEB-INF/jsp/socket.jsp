<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>
<head>
    <title>Spring Secured Sockets</title>
    <link href="<c:url value="/resources/styles/style.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/vendor/sockjs/sockjs.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/stomp/stomp.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/angular/angular-route.min.js" />"></script>

    <script src="<c:url value="/resources/scripts/app.js" />"></script>
    <script src="<c:url value="/resources/scripts/services/SocketService.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/indexController.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/loginController.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/socketController.js" />"></script>
    <script src="<c:url value="/resources/scripts/controllers/successController.js" />"></script>
    <script src="<c:url value="/resources/scripts/routes/router.js" />"></script>
</head>
<body ng-controller="socketController">
<h1>Socket Chat</h1>
<c:set var = "context" scope = "session" value = "${pageContext.request.contextPath}"/>
<div>
    <div>
        <input type="text" id="from" placeholder="Choose a nickname"/>
    </div>
    <br/>
    <div>
        <button id="connect" ng-click="connect('${context}');">Connect</button>
        <button id="subscribe" ng-click="subscribe();">Subscribe</button>
        <button id="disconnect" disabled="disabled" ng-click="disconnect();">Disconnect</button>
    </div>
    <br/>
    <div id="conversationDiv">
        <input type="text" id="text" placeholder="Write a message..."/>
        <button id="sendMessage" ng-click="sendMessage('${context}');">Send</button>
        <p id="response"></p>
    </div>
</div>

<a href="${context}/secured/success">Click to go back!</a>
<a href="${context}/">Click to start over (you will still be authenticated)!</a>

<!-- CSRF Token -->
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</body>
</html>