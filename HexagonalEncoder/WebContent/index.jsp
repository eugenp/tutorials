<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Encode Utility</title>
</head>
<body style="text-align: center">
	Welcome to Encode Utility
	<br>
	<br>
	<form id="getEncodedMessage" method="post">

		<label>Enter Text to Encode </label>
		 &nbsp;&nbsp; 
		<input type="text"	name="message"> <br> <br>
		<button type="submit" formaction="/hexagonalencoder/encode">Encode</button>
		<button type="submit" formaction="/hexagonalencoder/decode">Decode</button>

	</form>
</body>
</html>