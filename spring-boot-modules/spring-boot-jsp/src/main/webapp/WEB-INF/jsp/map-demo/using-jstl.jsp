<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demo - Using Map in JSP (JSTL)</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 3px;
        }
    </style>
</head>
<body>
<div>Movies in the Map Object (Using JSTL)</div>
<br/>
<table>
    <tr>
        <th>Code</th>
        <th>Movie Title</th>
    </tr>
    <c:forEach items="${movieMap}" var="entry">
        <tr>
            <td>
                ${entry.key}
            </td>
            <td>
                ${entry.value}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>