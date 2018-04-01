<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty layout or layout eq true}">
<html>
<head>
    <title>${title}</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<div id="header">
    页面头部
</div>
</c:if>