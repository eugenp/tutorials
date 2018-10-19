<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Signup</title>

<style>
.green {
	font-weight: bold;
	color: green;
}

.message {
	margin-bottom: 10px;
}

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
		<c:if test="${not empty message}">
			<div class="message green">${message}</div>
		</c:if>
	</div>

	<div style="width: 100%;">
		<form:form id="myForm" method="post"
			class="bs-example form-horizontal" action="signup.html" modelAttribute="student">
			<fieldset>
				<legend>Student Enrollment Signup Form</legend>

				<table>
					<tr>
						<td><label for="userNameInput" class="col-lg-3 control-label">User
								Name</label></td>
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
						<td><label for="firstNameInput"
							class="col-lg-3 control-label">First Name</label></td>
						<td><form:input type="text" class="form-control"
								path="firstName" id="firstNameInput" placeholder="First Name" />
							<form:errors path="firstName" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="lastNameInput" class="col-lg-3 control-label">Last
								Name</label></td>
						<td><form:input type="text" class="form-control"
								path="lastName" id="lastNameInput" placeholder="Last Name" /> <form:errors
								path="lastName" cssClass="error" /></td>
					</tr>

					<tr>
						<td><label for="dateOfBirthInput"
							class="col-lg-3 control-label">Date of Birth</label></td>
						<td><form:input type="text" class="form-control"
								path="dateOfBirth" id="dateOfBirthInput"
								placeholder="Date of Birth" /> <form:errors path="dateOfBirth"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="emailAddressInput"
							class="col-lg-3 control-label">Email Address</label></td>
						<td><form:input type="text" class="form-control"
								path="emailAddress" id="emailAddressInput"
								placeholder="Email Address" /> <form:errors path="emailAddress"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn btn-default">Cancel</button>
&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-primary" data-toggle="modal"
								data-target="#themodal">Submit</button>
						</td>

					</tr>
				</table>
			</fieldset>
		</form:form>
	</div>
</body>
</html>