<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sec:csrfMetaTags />
<title>Home Page</title>
</head>
<body>    
    <sec:authorize access="!isAuthenticated()">
        Login
    </sec:authorize>
    
    <sec:authorize access="isAuthenticated()">
        Logout
    </sec:authorize>
    
    <sec:authorize access="isAuthenticated()">
        <h2>
            Welcome back, <sec:authentication property="name" />
        </h2>         
        <sec:authorize access="hasRole('ADMIN')">
            Manage Users
        </sec:authorize>
        <form method="post">
            <sec:csrfInput />
            Text Field: <br /> <input type="text" name="textField" />
            <input type="submit" value="Submit form with CSRF input">
        </form>
        <sec:authorize url="/userManagement">
            <a href="/userManagement">Manage Users</a>
        </sec:authorize>
    </sec:authorize>
</body>
</html>