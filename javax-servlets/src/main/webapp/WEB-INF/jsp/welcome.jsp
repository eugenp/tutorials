<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hello Known User:</title>
</head>
<body style="background: ${uiColor}">
<form action="welcome" method="POST">
    <label>Your name: ${userName}</label>
    <label>Session param: ${sessionAttribute}</label>
    <button type="submit">Logout</button>
</form>
</body>
</html>
