<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image download examples</title>
</head>
<body>
<h1>Image download examples</h1>
<ul>
    <li><a href="<c:url value="/image-manual-response" />">Image manually add to response</a></li>
    <li><a href="<c:url value="/image-byte-array.jpg" />">Image Download <i>byte[]</i> Example</a></li>
    <li><a href="<c:url value="/image-response-entity.jpg" />">Image Download <i>ResponseEntity</i> Example</a></li>
    <li><a href="<c:url value="/image-resource.jpg" />">Image Download <i>Resource</i> Example</a></li>
</ul>
</body>
</html>
