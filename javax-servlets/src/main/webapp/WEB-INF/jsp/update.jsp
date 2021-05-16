<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>

	Hi, User : ${sessionData.getAttribute("userId")}

	<br> Your User Data has been updated as below :
	<br> User Name: ${sessionData.getAttribute("userName")}
	<br> Age : ${sessionData.getAttribute("age")}

</body>
</html>