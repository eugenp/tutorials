<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Sample Form</title>
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
            width: 70px;
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

    <h2>Phone Number</h2>
    <c:if test="${not empty message}">
        <div class="message green">${message}</div>
    </c:if>

    <form:form action="/spring-mvc-basics/addValidatePhone" modelAttribute="validatedPhone">

        <label for="phoneInput">Phone: </label>
        <form:input path="phone" id="phoneInput"/>
        <form:errors path="phone" cssClass="error"/>
        <br/>

        <input type="submit" value="Submit"/>
    </form:form>
</div>
</body>
</html>
