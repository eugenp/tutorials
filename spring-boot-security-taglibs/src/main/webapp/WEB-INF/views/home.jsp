<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
     <security:authorize access="isAuthenticated()">
        AUTHENTICATED
     </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        ADMIN ROLE
    </security:authorize>
</body>
</html>