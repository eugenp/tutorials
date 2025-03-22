<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    .first { background-color: lightgreen }
    .last { background-color: orange }
</style>
<head>
    <title>JSTL ForEach Example</title>
</head>
<body>
<h1>Movie List</h1>
<div>
    <table style="width: 50%">
        <tr>
            <th>varStatus.index</th>
            <th>varStatus.count</th>
            <th>Year</th>
            <th>Title</th>
        </tr>
        <c:forEach var="movie" items="${movieList}" varStatus="theLoop">
            <tr ${theLoop.first ? 'class="first"' : ''} ${theLoop.last ? 'class="last"' : ''}>
                <td>${theLoop.index}</td>
                <td>${theLoop.count}</td>
                <td>${movie.year}</td>
                <td>${movie.title}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>