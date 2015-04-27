<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<c:url value="/home.html" />">Schedule to Reddit</a>
    </div>
    
     <p class="navbar-text navbar-right">Logged in as 
        <b><sec:authentication property="principal.username" /></b>&nbsp;&nbsp;&nbsp;
        <a href="<c:url value="/logout" />">Logout</a>&nbsp;&nbsp;&nbsp;
    </p>
    
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="<c:url value="/posts" />">My Scheduled Posts</a></li>
        <li><a href="<c:url value="/post" />">Post to Reddit</a></li>
        <li><a href="<c:url value="/postSchedule" />">Schedule Post to Reddit</a></li>
      </ul>
      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>