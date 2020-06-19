<html>
<head>
    <title>Login</title>
</head>
<body style="margin-left: 30px;">
<h3>Login</h3>
<br>
<form action="/login" method="post">
    <#if (error?length > 0)??>
        <p style="color:darkred;">${error}</p>
    <#else>
    </#if>

    <label for="username">Username</label>
    <br>
    <input type="text" name="username">
    <br><br>
    <label for="password">Password</label>
    <br>
    <input type="password" name="password">
    <br><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Submit">
</form>
</body>
</html>