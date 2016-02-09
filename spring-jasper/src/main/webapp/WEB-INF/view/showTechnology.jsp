<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World</title>
</head>
<body>
	<h2>Jasper Report Example</h2>
	<form:form method="POST" action="showTechnologyReport.html"
		commandName="technologyForm">
		<table>
			<tr>
				<td>Please Enter your name :</td>
				<td><form:input path="inputName" id="inputName" /></td>
			</tr>
			<tr>
				<td>Select your favourite technologies</td>
				<td><form:checkboxes path="technologies"
						items="${technologies}" /></td>
				<td><form:errors path="inputName" class="cssErrors"></form:errors></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Generate Report" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>