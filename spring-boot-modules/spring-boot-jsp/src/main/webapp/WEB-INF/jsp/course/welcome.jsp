<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.baeldung.boot.jsp.coursewelcome.CourseWelcome" %>
<html>
<head>
    <title>Welcome to Course</title>
</head>
<body>
    <%
        CourseWelcome courseWelcomeObj = new CourseWelcome();
    %>
    <div><%= courseWelcomeObj.greeting("Kai")%></div>
    <div><%= CourseWelcome.staticWelcome("Spring Boot")%></div>
</body>
</html>