<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<style>
	.message{
		margin-top: 20px;
		padding: 10px;
		color:#FF0000;
		border: 1px solid;
		border-radius: 2px;
		background-color: #F5F6CE;
		border-color: #FF0000;
	}
</style>
</head>

<body>
	<h1>Login</h1>

	<form name='f' action="j_spring_security_check" method='POST'>

		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td>Remember Me:</td>
				<td><input type="checkbox" name="_spring_security_remember_me" /></td>
			</tr>
			<tr>
				<td><input name="submit" type="submit" value="submit" /></td>
			</tr>
		</table>
		
		
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

	</form>
	
			
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

</body>
</html>