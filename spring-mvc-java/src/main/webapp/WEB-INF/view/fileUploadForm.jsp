<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>File Upload Example</title>
	</head>
	
	<body>
	
		<h3>Enter The File to Upload (MultipartFile handling)</h3>
	
		<form:form method="POST" action="/spring-mvc-java/addFile1" enctype="multipart/form-data">
			
			<table>
				<tr>
					<td>Select a file to upload</td>
					<td><input type="file" name="file" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Submit" /></td>
				</tr>
			</table>
			
		</form:form>
		
		<br />
		
		<h3>Enter The File to Upload (HttpServletRequest handling)</h3>
		
		<form:form method="POST" action="/spring-mvc-java/addFile2" enctype="multipart/form-data">
			
			<table>
				<tr>
					<td>Select a file to upload</td>
					<td><input type="file" name="file" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Submit" /></td>
				</tr>
			</table>
			
		</form:form>
	
	</body>

</html>