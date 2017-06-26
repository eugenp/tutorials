<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
.error {
	color: #ff0000;
	font-size: 0.9em;
	font-weight: bold;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
<title>Student Login</title>
</head>
<body>
	<div style="width: 100%">
		<a href="login.html">Login</a> &nbsp;&nbsp;&nbsp; <a
			href="signup.html">Signup</a>
	</div>

	<div>
		<div>
			<div>
				<h1>Welcome to Online Student Enrollment Application</h1>
				<p>Login to explore the complete features!</p>
			</div>
		</div>

		<div></div>
	</div>

	<div>
		<fieldset>
			<legend>Student Enrollment Login Form</legend>
			<form:form id="myForm" method="post"
				class="bs-example form-horizontal" modelAttribute="studentLogin" action="login.html">
				<table>
					<tr>
						<td><label for="userNameInput" class="col-lg-3 control-label">User
								Name : </label></td>
						<td><form:input type="text" class="form-control"
								path="userName" id="userNameInput" placeholder="User Name" /> <form:errors
								path="userName" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="passwordInput" class="col-lg-3 control-label">Password</label>
						</td>
						<td><form:input type="password" class="form-control"
								path="password" id="passwordInput" placeholder="Password" /> <form:errors
								path="password" cssClass="error" /></td>

					</tr>
					<tr>
						<td colspan="2">
							<button class="btn btn-default">Cancel</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-primary">Login</button>
						</td>
					</tr>

				</table>

			</form:form>
		</fieldset>
	</div>


</body>
</html>