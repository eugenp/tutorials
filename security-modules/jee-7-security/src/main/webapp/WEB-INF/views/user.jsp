<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head></head>

<body>
    <h1>Welcome to the Restricted Admin page</h1>

    <a href="<c:url value="/logout" />">Logout</a>

</body>
</html>