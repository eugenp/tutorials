<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Hello New User:</title>
</head>
<body style="background: ${uiColor}">
    <form name="userForm" action="login" method="POST">
        <label>Your Name:</label>
        <input type="text" name="name"/>

        <label>UI Color:</label>
        <input type="text" name="color"/>

        <button type="submit">Confirm</button>
    </form>
</body>
</html>
