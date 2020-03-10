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
<br/>

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
<br/>

<h2>Submitted File with Data (<code>@RequestParam</code>)</h2>
<table>
    <tr>
        <td>Name :</td>
        <td>${name}</td>
    </tr>
    <tr>
        <td>Email :</td>
        <td>${email}</td>
    </tr>
    <tr>
        <td>OriginalFileName :</td>
        <td>${file.originalFilename}</td>
    </tr>
    <tr>
        <td>Type :</td>
        <td>${file.contentType}</td>
    </tr>
</table>

<br/>

<h2>Submitted File with Data (<code>@ModelAttribute</code>)</h2>
<table>
    <tr>
        <td>Name :</td>
        <td>${formDataWithFile.name}</td>
    </tr>
    <tr>
        <td>Email :</td>
        <td>${formDataWithFile.email}</td>
    </tr>
    <tr>
        <td>OriginalFileName :</td>
        <td>${formDataWithFile.file.originalFilename}</td>
    </tr>
    <tr>
        <td>Type :</td>
        <td>${formDataWithFile.file.contentType}</td>
    </tr>
</table>
</body>
</html>