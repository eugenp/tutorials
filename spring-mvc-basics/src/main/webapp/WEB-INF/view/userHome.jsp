<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>User Form</title>
    <style>
        body {
            background-color: #eee;
            font: helvetica;
        }

        #container {
            width: 500px;
            background-color: #fff;
            margin: 30px auto;
            padding: 30px;
            border-radius: 5px;
        }

        .green {
            font-weight: bold;
            color: green;
        }

        .message {
            margin-bottom: 10px;
        }

        label {
            width: 160px;
            display: inline-block;
        }

        input {
            display: inline-block;
            margin-right: 10px;
        }

        form {
            line-height: 160%;
        }

        .hide {
            display: none;
        }

        .error {
            color: red;
            font-size: 0.9em;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div id="container">

    <h2>New User</h2>
    <c:if test="${not empty message}">
        <div class="message green">${message}</div>
    </c:if>

    <form:form action="/spring-mvc-basics/user" modelAttribute="newUserForm">

        <form:errors path="" cssClass="error"/>
        <br/>

        <label for="email">Email: </label>
        <form:input path="email" id="emailInput"/>
        <form:errors path="email" cssClass="error"/>
        <br/>

        <label for="verifyEmail">Verify email: </label>
        <form:input path="verifyEmail" id="verifyEmailInput"/>
        <form:errors path="verifyEmail" cssClass="error"/>
        <br/>

        <label for="password">Password: </label>
        <form:input path="password" type="password" id="passwordInput"/>
        <form:errors path="password" cssClass="error"/>
        <br/>

        <label for="verifyPassword">Verify password: </label>
        <form:input path="verifyPassword" type="password" id="verifyPasswordInput"/>
        <form:errors path="verifyPassword" cssClass="error"/>
        <br/>

        <input type="submit" value="Submit"/>
    </form:form>
</div>
</body>
</html>
