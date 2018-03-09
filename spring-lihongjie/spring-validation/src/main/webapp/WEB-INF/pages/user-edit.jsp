<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Edit User page</title>
</head>
<body>
	<h1>Edit user</h1>
	<h2>user name : ${user.username}</h2>
	<form:form method="POST" commandName="user"
		action="${pageContext.request.contextPath}/users/1">
		<table>
			<tbody>
				<tr>
					<td>Shop name:</td>
					<td><form:input path="username" /></td>
					<td><form:errors path="username" cssStyle="color: red;" /></td>
				</tr>

				<tr>
					<td><input type="submit" value="Edit" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>
	<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
