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
    Current user name: <sec:authentication property="principal.username" /> 
    <br/>
    Current user roles: <sec:authentication property="principal.authorities" /> 
</div>
</body>
</html>