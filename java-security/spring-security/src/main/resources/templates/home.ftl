<html>
<head>
    <title>Home Page</title>
</head>
<body style="margin-left: 30px;">
<h1>Welcome ${username}!</h1>
<p><strong>Role</strong>: ${role}</p>
<p><strong>Permissions</strong></p>
<p>${permissions}</p>
<a href="/admin">Admin only</a>
<#if adminContent??>
  ${adminContent}
</#if>
<br>
<form role="form" action="/logout" method="POST">
   <input type="Submit" value="Logout" />
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>