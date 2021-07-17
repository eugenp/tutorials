<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Example - Register an Employee</title>
</head>
<body>
	<h3>Welcome, Enter The Employee Details</h3>
	<h4>${message}</h4>

	<form:form method="POST" action="${pageContext.request.contextPath}/addEmployee" modelAttribute="employee">
		<table>
			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="id">Id</form:label></td>
				<td><form:input path="id" /></td>
			</tr>
			<tr>
				<td><form:label path="contactNumber">Contact Number</form:label></td>
				<td><form:input path="contactNumber" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Submit" /></td>
				<td><input type="submit" name="cancel" value="Cancel" /></td>
			</tr>
		</table>
	</form:form>

</body>

</html>