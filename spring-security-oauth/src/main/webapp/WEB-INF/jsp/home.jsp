<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>

<title>Schedule to Reddit</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Schedule to Reddit</a>
    </div>
    
    <p class="navbar-text navbar-right">Logged in as 
        <b><sec:authentication property="principal.username" /></b>&nbsp;&nbsp;&nbsp;
    </p>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="posts">My Scheduled Posts</a></li>
        <li><a href="post">Post to Reddit</a></li>
        <li><a href="postSchedule">Schedule Post to Reddit</a></li>
      </ul>
      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="container">
        <h1>Welcome, <small><sec:authentication property="principal.username" /></small></h1>
        <br>
        <a href="posts" class="btn btn-primary">My Scheduled Posts</a>
        <a href="post" class="btn btn-primary">Post to Reddit</a>
        <a href="postSchedule" class="btn btn-primary">Schedule Post to Reddit</a>
</div>
</body>
</html>