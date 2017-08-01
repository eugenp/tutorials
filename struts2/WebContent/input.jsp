<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Baledung Struts</title>
</head>
<body>
	<form method="get" action="/MyStrutsApp/tutorial/car.action">
		<p>Welcome to Baeldung Struts 2 app</p>
		<p>Which car do you like !!</p>
		<p>Please choose ferrari or bmw</p>
		<select name="carName">
			<option value="Ferrari" label="ferrari" />
			<option value="BMW" label="bmw" />
		</select> <input type="submit" value="Enter!" />

	</form>
</body>
</html>