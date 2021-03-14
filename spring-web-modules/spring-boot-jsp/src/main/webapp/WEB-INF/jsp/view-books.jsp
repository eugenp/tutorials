<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Books</title>

    <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">

</head>
<body>
    <table>
        <thead>
            <tr>
                <th>ISBN</th>
                <th>Name</th>
                <th>Author</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.isbn}</td>
                    <td>${book.name}</td>
                    <td>${book.author}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>