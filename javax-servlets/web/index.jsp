<%--
  Created by IntelliJ IDEA.
  User: shubham
  Date: 26/11/16
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form</title>
</head>
<body>

<form action="informationServlet" method="POST">

    Name:<input type="text" name="userName"/><br/><br/>
    Planet:
    <select name="userPlanet">
        <option>Mercury</option>
        <option>Venus</option>
        <option>Mars</option>
    </select>
    <br/><br/>

    <input type="submit" value="Submit"/>
</form>

</body>
</html>
