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
    </c:when>
    <c:otherwise> Sorry, error occurred.</c:otherwise>
</c:choose>
</body>
</html>