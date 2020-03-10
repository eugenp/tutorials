<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>File Upload Example</title>
</head>

<body>

<h3>Enter The File to Upload (Single file)</h3>

<form:form method="POST" action="/spring-mvc-java/uploadFile" enctype="multipart/form-data">

    <table>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>

</form:form>

<br/>

<h3>Enter The Files to Upload (Multiple files)</h3>

<form:form method="POST" action="/spring-mvc-java/uploadMultiFile" enctype="multipart/form-data">

    <table>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="files"/></td>
        </tr>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="files"/></td>
        </tr>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="files"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>

</form:form>

<br/>

<h3>Fill the Form and Select a File (<code>@RequestParam</code>)</h3>

<form:form method="POST" action="/spring-mvc-java/uploadFileWithAddtionalData" enctype="multipart/form-data">

    <table>
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>

</form:form>

<br/>

<h3>Fill the Form and Select a File (<code>@ModelAttribute</code>)</h3>

<form:form method="POST" action="/spring-mvc-java/uploadFileModelAttribute" enctype="multipart/form-data">

    <table>
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>

</form:form>

</body>

</html>