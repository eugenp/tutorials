<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 1/19/16
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <div>
        <div>
            <h4>Send Email</h4>
            <form action="mail/send" method="get">
                <input type="submit" value="Send Email">
            </form>
        </div>
    </div>
</body>
</html>