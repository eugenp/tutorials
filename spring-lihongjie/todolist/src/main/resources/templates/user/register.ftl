<#import "/spring.ftl" as spring>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" key="IE=edge">
	<meta name="viewport" key="width=device-width, initial-scale=1">
	<title>register</title>
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/public/css/console.css">
	<link rel="stylesheet" type="text/css" href="/public/css/simple-sidebar.css">
	<script src="/webjars/jquery/2.2.4/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="/webjars/coffee-script/1.10.0/coffee-script.min.js"></script>
	<script type="text/coffeescript">
    </script>
</head>
<body>
<div id="page-key-wrapper">
    <section class="subtitle">
        <h2>注册</h2>
    </section>

    <section class="data-table">
        <div class="container-fluid">
	        <form style="margin:20px 10px;" action="/register" method="post">
            <@spring.bind "form.avatar" />
			        <img style="width:50px;height:50px;margin-bottom:20px;" src="${spring.status.value?default("/public/misc/avatar.png")}">
            <@spring.bind "form.email" />
			        <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
			        <label for="email">邮箱地址</label>
			        <input type="email" name="${spring.status.expression}" class="form-control" placeholder="Email" value="${spring.status.value?default("")}">
			        <p class="error"><@spring.showErrors "" /></p>
		        </div>

          <@spring.bind "form.name" />
		        <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
			        <label for="name">名称</label>
			        <input type="text" name="${spring.status.expression}" class="form-control" placeholder="Name" value="${spring.status.value?default("")}">
			        <p class="error"><@spring.showErrors "" /></p>
		        </div>

          <@spring.bind "form.password" />
		        <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
			        <label for="password">密码</label>
			        <input type="password" name="${spring.status.expression}" class="form-control" placeholder="Password" value="${spring.status.value?default("")}">
			        <p class="error"><@spring.showErrors "" /></p>
		        </div>
		        <div class="checkbox">
			        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
			        <button type="submit" class="btn btn-default">注册用户</button>
	        </form>
        </div>
    </section>
  </div>
</body>
</html>
