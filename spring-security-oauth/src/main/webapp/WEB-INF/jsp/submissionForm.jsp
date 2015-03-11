<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Schedule to Reddit</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="/resources/validator.js" />"></script>
<style type="text/css">
.btn.disabled{
background-color: #ddd;
border-color: #ddd;
}

.btn.disabled:hover{
background-color: #ddd;
border-color: #ddd;
}
</style>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="info">Schedule to Reddit</a>
    </div>
    
    <p class="navbar-text navbar-right">Logged in as <b><c:out value="${username}"/></b>&nbsp;&nbsp;&nbsp;</p>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="posts">My Scheduled Posts</a></li>
        <li class="active"><a href="post">Post to Reddit</a></li>
        <li><a href="postSchedule">Schedule Post to Reddit</a></li>
      </ul>
      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="container">
<h1>Post to Reddit</h1>
<form action="submit" method="post" role="form" data-toggle="validator">
<div class="row">
<div class="form-group">
    <label class="col-sm-3">Title</label>
    <span class="col-sm-9"><input name="title" placeholder="title" class="form-control" required data-minlength="3"/></span>
</div>
<br><br>
<div class="form-group">
    <label class="col-sm-3">Url</label>
    <span class="col-sm-9"><input name="url" type="url" placeholder="url" class="form-control" required data-minlength="3"/></span>
</div>
<br><br>  
<div class="form-group">
    <label class="col-sm-3">Subreddit</label>
    <span class="col-sm-9"><input name="sr" placeholder="Subreddit (e.g. kitten)" class="form-control" required data-minlength="3"/></span>
</div>
<br><br>
<div>
<label class="col-sm-3">Send replies to my inbox</label> <span class="col-sm-9"><input type="checkbox" name="sendreplies" value="true"/></span> 
</div>
<br><br>
 
    <c:if test="${iden != null}">
    <input type="hidden" name="iden" value="${iden}"/>
    
	<div class="form-group">   
	    <label class="col-sm-3">Captcha</label>
	    <span class="col-sm-9"><input name="captcha" placeholder="captcha" class="form-control"/></span>
	</div>
	<br><br>
    <img src="http://www.reddit.com/captcha/${iden}" alt="captcha" width="200"/>
    </c:if>
    <br><br>
    <span class="col-sm-3"><button type="submit" class="btn btn-primary">Post</button></span>
   </div>
</form>
</div>
</body>
</html>