<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Hello Known User:</title>
</head>
<body style="background: ${uiColor}">
    <form action="welcome" method="POST">
        <label>Your name: ${userName}</label><br/>
        <label>Session param: ${sessionAttribute}</label><br/>
        <button type="submit">Logout</button>
    </form>
</body>
</html>
