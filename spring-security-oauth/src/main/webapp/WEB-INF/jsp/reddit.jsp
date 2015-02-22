<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Spring Security OAuth</title>
</head>
<body>
<c:choose>
    <c:when test="${info != null}">
        <h1>Your Reddit Info</h1>
        <b>Your reddit username is </b>${info}
        <br>
        <a href="post">Submit to Reddit</a>
    </c:when>
    <c:otherwise> 
        <b>Sorry, error occurred</b> 
        <br><br>
        <div>${error}</div>
    </c:otherwise>
</c:choose>
</body>
</html>