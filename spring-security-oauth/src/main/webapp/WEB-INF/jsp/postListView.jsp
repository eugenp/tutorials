<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Spring Security OAuth</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="info">Spring Security OAuth</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">My Scheduled Posts</a></li>
        <li><a href="post">Submit Post</a></li>
        <li><a href="postSchedule">Schedule Post</a></li>
      </ul>
      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="container">
<h1>My Scheduled Posts</h1>
<table class="table table-bordered">
<c:forEach var="post" items="${posts}" >
<thead>
<tr>
<th>Post title</th>
<th>Submission Date</th>
<th>Notes</th>
</tr>
</thead>
    <tr <c:if test="${post.isSent()}"> class="success"</c:if>>
        <td><c:out value="${post.getTitle()}"/></td>
        <td><c:out value="${post.getSubmissionDate()}"/></td>
        <td><c:out value="${post.getSubmissionResponse()}"/></td>
    </tr>
</c:forEach>
</table>
</div>
</body>
</html>