<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Spring Security OAuth</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>
<body>
<div class="container">
<c:choose>
    <c:when test="${info != null}">
        <h1>Your Reddit Info</h1>
        <b>Your reddit username is </b>${info}
        <br><br><br>
        <a href="post" class="btn btn-primary">Submit to Reddit</a>
    </c:when>
    <c:otherwise> 
        <b>Sorry, error occurred</b> 
        <br><br>
        <div>${error}</div>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>