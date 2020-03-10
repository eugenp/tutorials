<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Context and Servlet Initialization Parameters</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h2>Please fill the form below:</h2>
<form action="${pageContext.request.contextPath}/userServlet" method="post">
    <label for="name"><strong>Name:</strong></label>
    <input type="text" name="name" id="name">
    <label for="email"><strong>Email:</strong></label>
    <input type="text" name="email" id="email">
    <input type="submit" value="Send">
</form>
</body>
</html>
