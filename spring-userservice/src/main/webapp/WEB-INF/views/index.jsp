<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome!</title>
</head>
<body>

<c:url value="/register" var="registerUrl" />
<c:url value="/login" var="loginUrl"/>
<c:url value="/logout" var="logoutUrl"/>

<a href="${registerUrl }">Register</a>
<br /><br/>
<sec:authorize access="!isAuthenticated()">
<a href="${loginUrl }">Login</a>
</sec:authorize>
<br><br>
${message }
<br><br>
<sec:authorize access="isAuthenticated()">
Hello, ${name }!
<br>
<br>
<a href="${logoutUrl }">Logout</a>
</sec:authorize>

</body>
</html>