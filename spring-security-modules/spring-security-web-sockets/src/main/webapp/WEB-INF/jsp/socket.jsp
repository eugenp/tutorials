<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html ng-app='angularApp'>

    <head>
        <title>Spring Secured Sockets</title>
        <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/styles/socket.css"/>" rel="stylesheet">

        <script src="<c:url value="/resources/vendor/sockjs/sockjs.min.js" />"></script>
        <script src="<c:url value="/resources/vendor/stomp/stomp.min.js" />"></script>
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

    <body ng-controller="socketController">
        <c:set var="context" scope="session" value="${pageContext.request.contextPath}"/>
        <main>
            <div class="wrapper">
                <h1>Socket Chat</h1>
                <div id="all">
                    <h3>(Chat With Everyone)</h3>
                    <input type="text" id="from" placeholder="Choose Your Username"/>
                    <button id="connectAll" ng-click="connectAll('${context}');">Connect All</button>
                    <button id="subscribeAll" ng-click="subscribeAll();">Subscribe All</button>
                </div>
                <div id="specific">
                    <h3>(Chat With A Specific User)</h3>
                    <input type="text" id="to" placeholder="Choose a Recipient"/>
                    <button id="connectSpecific" ng-click="connectSpecific('${context}');">Connect Specific</button>
                    <button id="subscribeSpecific" ng-click="subscribeSpecific();">Subscribe Specific</button>
                </div>
                <div id="conversationDiv">
                    <h3>(Send and See Messages)</h3>
                    <input type="text" id="text" placeholder="Write a message..."/>
                    <button id="sendMessage" ng-click="sendMessage('${context}');">Send</button>
                    <div id="response"></div>
                    <button id="disconnect" disabled="disabled" ng-click="disconnect();">Disconnect</button>
                </div>
                <a href="${context}/secured/success">Click to go back!</a>
                <a href="${context}/">Click to start over (you will still be authenticated)!</a>
            </div>
        </main>
        <!-- CSRF Token -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </body>

</html>