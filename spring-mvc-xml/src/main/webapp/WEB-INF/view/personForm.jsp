<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Form Example - Register a Person</title>
	</head>
	
	<style>
	
		.error {
			color: #ff0000;
		}
		
	</style>
	
	<body>
	
		<h3>Welcome, Enter The Person Details</h3>
	
		<form:form method="POST" action="/spring-mvc-xml/addPerson" modelAttribute="person" enctype="multipart/form-data">
			
			<form:errors path="*" cssClass="error"/>
			
			<table>
				<tr>
					<td><form:label path="name">Name</form:label></td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="password">Password</form:label></td>
					<td><form:password path="password" /></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
				    <td><form:label path="sex">Sex</form:label></td>
				    <td>
				        Male: <form:radiobutton path="sex" value="M"/> <br/>
				        Female: <form:radiobutton path="sex" value="F"/>
				    </td>
				</tr>
				<tr>
				    <td><form:label path="job">Job</form:label></td>
				    <td>
				        <form:radiobuttons items="${job}" path="job" /> 
				    </td>
				</tr>
				<tr>
				    <td><form:label path="country">Country</form:label></td>
				    <td>
				        <form:select path="country" items="${country}" />
				    </td>
				</tr>
				<tr>
				    <td><form:label path="book">Book</form:label></td>
				    <td>
				        <form:select path="book">
				        	<form:option value="-" label="--Please Select"/>
	            			<form:options items="${books}" />
	            		</form:select>
				    </td>
				</tr>
				<tr>
				    <td><form:label path="fruit">Fruit</form:label></td>
				    <td>
				        <form:select path="fruit" items="${fruit}" multiple="true"/>
				    </td>
				</tr>
				<tr>
					<td><form:label path="receiveNewsletter">Receive newsletter</form:label></td>
					<td><form:checkbox path="receiveNewsletter" rows="3" cols="20"/></td>
				</tr>
				<tr>
		            <td><form:label path="hobbies">Hobbies</form:label></td>
		            <td>
		                Bird watching: <form:checkbox path="hobbies" value="Bird watching"/>
		                Astronomy: <form:checkbox path="hobbies" value="Astronomy"/>
		                Snowboarding: <form:checkbox path="hobbies" value="Snowboarding"/>
		            </td>
	        	</tr>
	        	<tr>
		            <td><form:label path="favouriteLanguage">Favourite languages</form:label></td>
		            <td>
		                <form:checkboxes items="${favouriteLanguage}" path="favouriteLanguage" /> 
		            </td>
	        	</tr> 
				<tr>
					<td><form:label path="notes">Notes</form:label></td>
					<td><form:textarea path="notes" rows="3" cols="20"/></td>
				</tr>
				<tr>
					<td><form:label path="file">Select a file to upload</form:label></td>
					<td><input type="file" name="file" /></td>
				</tr>
				<tr>
					<td><form:hidden path="id" value="12345"/></td>
				</tr>
				<tr>
					<td><input type="submit" value="Submit" /></td>
				</tr>
				
			</table>
			
		</form:form>
	
	</body>

</html>