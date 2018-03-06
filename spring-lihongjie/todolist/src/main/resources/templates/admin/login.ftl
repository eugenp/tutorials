<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" key="text/html; charset=UTF-8"/>
	<title>Admin Sign in</title>

	<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="/public/css/login.css" rel="stylesheet" media="screen"/>
	<script src="//cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
	<script src="/webjars/coffee-script/1.10.0/coffee-script.min.js"></script>
</head>
<body class="red-bg">
<div class="container">
	<form class="login-form" action="#" method="post">
		<h2>Admin Login</h2>
		<hr />
        <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
            <label id="error-label" style="margin:0 0 10px 0;">账号和密码不匹配</label>
        </#if>
		<div class="form-group">
			<input type="text" name="username" class="form-control" placeholder="Email" value="nonocast@gmail.com" />
		</div>
		<div class="form-group">
			<input type="password" name="password" class="form-control" placeholder="Password"/>
		</div>
		<div class="checkbox">
			<label>
				<input type="checkbox" name="remember-me" class="checkbox-login"/> Remember me
			</label>
		</div>
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
		<button type="submit" class="btn btn-info btn-login">Sign in</button>
	</form>
</div>
</body>
</html>
