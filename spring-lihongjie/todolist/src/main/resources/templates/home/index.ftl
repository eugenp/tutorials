<#import "/lib/auth.ftl" as auth>

<!doctype html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${version}-${profile}</title>

	<link rel="shortcut icon" href="public/favicon.ico">
	<link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="/public/css/todos.css">

	<script src="//cdn.bootcss.com/react/15.2.1/react.min.js"></script>
	<script src="//cdn.bootcss.com/react/15.2.1/react-dom.min.js"></script>
	<script src="//cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
	<script src="//cdn.bootcss.com/classnames/2.2.5/dedupe.min.js"></script>
	<script src="//cdn.bootcss.com/moment.js/2.14.1/moment-with-locales.min.js"></script>
	<script src="//cdn.bootcss.com/Director/1.2.8/director.min.js"></script>
</head>
<body>
	<section class="todoapp"
					 token="${token}"
					 from="${auth.username}"
					 username=${username}
					 avatar=${avatar}>
	</section>
	<script type="text/javascript" src="/public/script/bundle.js"></script>
</body>
</html>