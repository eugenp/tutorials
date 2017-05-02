<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring Dispatcher</title>
    <link rel="stylesheet" href="${ctx}/<spring:theme code='styleSheet'/>" type="text/css"/>
</head>
<body>
    <h2>Hello World!</h2>
    <br/>
    <a href="${ctx}/?theme=example">Switch Theme!</a>
    <a href="${ctx}/?theme=default">Switch Theme!</a>
    <br/>
    <br/>
    <br/>
    <form action="${ctx}/upload" method="post" enctype="multipart/form-data">
        <label>Upload a file! </label><input type="file" name="file"/>
        <input type="submit" value="Upload File"/>
    </form>
    <br/>
    <br/>
    ${message}
</body>
</html>
