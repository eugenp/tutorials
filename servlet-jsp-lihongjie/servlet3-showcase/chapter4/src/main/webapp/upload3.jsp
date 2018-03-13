
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/upload3" method="post" enctype="multipart/form-data">

    file1: <input type="file" name="file1"><br/>

    <input type="submit" value="上传">

</form>

</body>
</html>