<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Baeldung - Spring JNA JNDI</title>
</head>
<body>
<c:forEach var="foo" items="${foos}">
    <p>
        <c:out value="${foo.name}"/>
    </p>
</c:forEach>
</body>
</html>