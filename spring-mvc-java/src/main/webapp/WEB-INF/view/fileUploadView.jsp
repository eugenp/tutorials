<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Spring MVC File Upload</title>
	</head>
	<body>
		
		<h2>Submitted File (Single)</h2>
		<table>
			<tr>
				<td>OriginalFileName :</td>
				<td>${file.originalFilename}</td>
			</tr>
			<tr>
				<td>Type :</td>
				<td>${file.contentType}</td>
			</tr>
		</table>
		<br />
		
		<h2>Submitted Files (Multiple)</h2>
		<table>
			<c:forEach items="${files}" var="file">	
				<tr>
					<td>OriginalFileName :</td>
					<td>${file.originalFilename}</td>
				</tr>
				<tr>
					<td>Type :</td>
					<td>${file.contentType}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>