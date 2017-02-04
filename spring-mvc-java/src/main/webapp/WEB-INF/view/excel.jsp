<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excel Processing</title>
</head>
<body>
<c:url value="/uploadExcelFile" var="uploadFileUrl" />
<c:url value="/excelProcessing" var="resetUrl" />
<c:url value="/readJExcel" var="readJExcelUrl" />
<c:url value="/writeJExcel" var="writeJExcelUrl" />
<c:url value="/readPOI" var="readPOIUrl" />
<c:url value="/writePOI" var="writePOIUrl" />

<form method="post" enctype="multipart/form-data" action="${uploadFileUrl}" >
<input type="file" name="file" accept=".xls,.xlsx"/>
<input type="submit" value="Upload file"/>
</form>
<br />
${message }
<br /> <br />
<form method="GET" action="${resetUrl}" >
<input type="submit" value="Reset" />
</form>
<br /> <br />
<a href="${readJExcelUrl}">Read file using JExcel</a> &nbsp;&nbsp;&nbsp;
<a href="${readPOIUrl}">Read file using Apache POI</a>
<br /> <br /> 

File content:
<c:if test="${not empty data}">
<table style="border:1px solid black;border-collapse:collapse;">
<c:forEach items="${data}" var="row">
<tr>
    <c:forEach items="${row.value}" var="cell">
        <td style="border:1px solid black">${cell}</td>
    </c:forEach>
</tr>   
</c:forEach>
</table>
</c:if>
<br /> <br />
<form action="${writeJExcelUrl}" method="POST">
<input type="submit" value="Write to file using JExcel" />
</form>
<br />
<form action="${writePOIUrl}" method="POST">
<input type="submit" value="Write to file using Apache POI" />
</form>

   
</body>
</html>