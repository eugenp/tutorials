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
    <sec:authorize access="isAnonymous()">
        ANONYMOUS
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        AUTHENTICATED    
        <sec:authorize access="hasRole('ADMIN')">
            ADMIN ROLE
        </sec:authorize>
        <h2>
            principal.username:
            <sec:authentication property="principal.username" />
        </h2>
        <form>
            <sec:csrfInput />
            Text Field: <br /> <input type="text" name="textField" />
        </form>
    </sec:authorize>
</body>
</html>