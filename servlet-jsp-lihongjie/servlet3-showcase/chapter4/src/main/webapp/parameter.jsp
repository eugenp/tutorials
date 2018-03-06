<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/requestParameter?name=get" method="post">

        <input type="hidden" name="name" value="post1">
        <input type="hidden" name="name" value="post2">

        <input type="submit" value="提交">

    </form>

</body>
</html>