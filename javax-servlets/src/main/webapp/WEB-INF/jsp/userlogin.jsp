<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<p>Update your User Details:</p>

	<form action="update">
		User ID: <input type="text" name="userId"
			value='<%=request.getAttribute("id")%>' disabled /><br /> User Name:
		<input type="text" name="userName" /> Age: <input type="number"
			name="age" /> <br /> <input type="submit" value="Update" />
	</form>
</body>
</html>