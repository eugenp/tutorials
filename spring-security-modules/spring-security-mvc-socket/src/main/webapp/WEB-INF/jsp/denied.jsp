<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>

    <head>
        <title>Spring Secured Sockets</title>
        <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/styles/denied.css"/>" rel="stylesheet">
        <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    </head>

    <body>
        <main>
            <div class="wrapper">
                <h1>ACCESS DENIED!</h1>
                <a href="${pageContext.request.contextPath}/login">Click to login.</a>
            </div>
        </main>
    </body>
</html>