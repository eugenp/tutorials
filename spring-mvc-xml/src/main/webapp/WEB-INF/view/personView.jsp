<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>
<body>

<h2>Submitted Person Information</h2>
<table>
    <tr>
        <td>Id :</td>
        <td>${person.id}</td>
    </tr>
    <tr>
        <td>Name :</td>
        <td>${person.name}</td>
    </tr>
    <tr>
        <td>Date of birth :</td>
        <td>${person.dateOfBirth}</td>
    </tr>
    <tr>
        <td>Password :</td>
        <td>${person.password}</td>
    </tr>
    <tr>
        <td>Sex :</td>
        <td>${person.sex}</td>
    </tr>
    <tr>
        <td>Job :</td>
        <td>${person.job}</td>
    </tr>
    <tr>
        <td>Country :</td>
        <td>${person.country}</td>
    </tr>
    <tr>
        <td>Fruit :</td>
        <td><c:forEach items="${person.fruit}" var="current">[<c:out value="${current}"/>]</c:forEach></td>
    </tr>
    <tr>
        <td>Book :</td>
        <td>${person.book}</td>
    </tr>
    <tr>
        <td>Receive Newsletter :</td>
        <td>${person.receiveNewsletter}</td>
    </tr>
    <tr>
        <td>Hobbies :</td>
        <td><c:forEach items="${person.hobbies}" var="current">[<c:out value="${current}"/>]</c:forEach></td>
    </tr>
    <tr>
        <td>Favourite Languages :</td>
        <td><c:forEach items="${person.favouriteLanguage}" var="current">[<c:out value="${current}"/>]</c:forEach></td>
    </tr>
    <tr>
        <td>Notes :</td>
        <td>${person.notes}</td>
    </tr>
</table>
</body>
</html>
