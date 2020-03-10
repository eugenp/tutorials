<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head></head>

<body>
<h1>This is the body of the sample admin page</h1>

This page is only visible to an admin
<br/>

<a href="<c:url value="/perform_logout" />">Logout</a>

</body>
</html>