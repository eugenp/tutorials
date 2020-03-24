<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Excel Processing</title>
</head>
<body>
    <c:url value="/uploadExcelFile" var="uploadFileUrl" />
    <c:url value="/excelProcessing" var="resetUrl" />
    <c:url value="/readPOI" var="readPOIUrl" />

    <form method="post" enctype="multipart/form-data" action="${uploadFileUrl}">
        <input type="file" name="file" accept=".xls,.xlsx" /> 
        <input type="submit" value="Upload file" />
    </form>
    <br />
    <form method="GET" action="${resetUrl}">
        <input type="submit" value="Reset" />
    </form>
    <br /> ${message }
    <br />
    <br />

    <form action="${readPOIUrl }">
        <input type="submit" value="Display file content" />
    </form>
    <br />
    <br />

    <c:if test="${not empty data}">
        <table style="border: 1px solid black; border-collapse: collapse;">
            <c:forEach items="${data}" var="row">
                <tr>
                    <c:forEach items="${row.value}" var="cell">
                        <td style="border:1px solid black;height:20px;width:100px;
                        background-color:${cell.bgColor};color:${cell.textColor};
                        font-weight:${cell.textWeight};font-size:${cell.textSize}pt;">
                        ${cell.content}
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</body>
</html>