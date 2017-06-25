 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Spring Security JSP Authorize</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand">Spring Security JSP Authorize</a>
      </div>
    </div>
</nav>

<div class="container">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        Only admins can see this message
    </sec:authorize>
    
    <sec:authorize access="hasRole('ROLE_USER')">
        Only users can see this message
    </sec:authorize>
    <hr/>
    
    <sec:authorize url="/admin">
        Only users who can call "/admin" URL can see this message
    </sec:authorize>
</div>
</body>
</html>