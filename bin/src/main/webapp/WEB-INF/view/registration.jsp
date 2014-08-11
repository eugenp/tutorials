<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Registration</title>
</head>
<body>
<H1> This is the registration page</H1>
<form:form action="/user/registration" commandName="user" method="POST" enctype="utf8" role="form">
                  
                    
                       
                            <label class="control-label" for="user-firstName">First Name:</label>
                            <form:input id="user-firstName" path="firstName" />
                            <form:errors id="error-firstName" path="firstName" />
               
                  
                    </form:form>
</body>
</html>