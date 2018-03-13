<#import "/spring.ftl" as spring>
<#import "/lib/auth.ftl" as auth>
<#import "/lib/pagination.ftl" as pagination>

<#macro view title="admin" sidebar="console" header="">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" key="IE=edge">
	<meta name="viewport" key="width=device-width, initial-scale=1">
	<title>console - ${title}</title>
	<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">

	<link rel="stylesheet" type="text/css" href="/public/css/admin.css">
	<link rel="stylesheet" type="text/css" href="/public/css/simple-sidebar.css">

	<script src="//cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
	<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/webjars/coffee-script/1.10.0/coffee-script.min.js"></script>

	<script type="text/coffeescript">
		$.app = $.app or {}
		$.app.page = '${sidebar}'
	</script>
	<script type="text/coffeescript" src="/private/script/admin.coffee"></script>
    ${header}
</head>
<body>
<div class="navbar navbar-default navbar-custom">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">TODOLIST APPLICATION CONSOLE</a>
    <#--<a class="navbar-brand" href="#">Todolist Application Console</a>-->
		</div>
		<div class="navbar-right visible-sm visible-md visible-lg">
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle avater" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						<img src="/public/misc/avatar.png" class="img-responsive circle" alt="Responsive image">
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">修改密码</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="/logout">退出登录</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<div id="wrapper">
	<!-- Sidebar -->
	<div id="sidebar-wrapper">
		<ul class="sidebar-nav">
			<li><a href="/admin/console">控制台</a></li>
			<li><a href="/admin/users">用户管理</a></li>
			<li><a href="/admin/users/create">新建用户</a></li>
			<li><a href="/admin/tasks">任务管理</a></li>
			<li><a href="/admin/tasks/create">新建任务</a></li>
			<li><a href="/admin/tokens">API登录凭证</a></li>
			<li><a href="/admin/backup">备份和还原</a></li>
		</ul>
	</div>
	<!-- /#sidebar-wrapper -->
	<!-- Page Content -->
	<div id="page-content-wrapper">
		<a href="#menu-toggle" id="menu-toggle" class="sidebar-menu"><i class="fa fa-angle-double-left"></i></a>
        <#nested>
	</div>
	<!-- /#page-key-wrapper -->
</div>

</body>
</html>
</#macro>
