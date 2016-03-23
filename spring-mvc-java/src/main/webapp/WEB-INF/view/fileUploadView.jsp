<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Spring MVC File Upload</title>
	</head>
	<body>
		<h2>Submitted File</h2>
		<table>
			<tr>
				<td>OriginalFileName :</td>
				<td>${fileName}</td>
			</tr>
			<tr>
				<td>Type :</td>
				<td>${fileType}</td>
			</tr>
		</table>
	</body>
</html>