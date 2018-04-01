
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">

    name:<input type="text" name="name"><br/>

    file1: <input type="file" name="file1"><br/>

    <input type="submit" value="上传">

</form>

</body>
</html>