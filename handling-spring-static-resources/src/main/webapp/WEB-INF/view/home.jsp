<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/myCss.css"/>">
    <script type="text/javascript" src="<c:url value="/js/foo.js" />">
    </script>
    <title>Home</title>
</head>
<body>
<h1>
    This is Home!
</h1>
<img alt="bunny hop image" src="<c:url value="files/myImage.png" />">
<input type="button" value="Click to Test Js File" onclick="testing();"/>
</body>
</html>
