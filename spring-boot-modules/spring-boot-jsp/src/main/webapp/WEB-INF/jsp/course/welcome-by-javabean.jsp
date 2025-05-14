<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to Course</title>
</head>
<body>
    <p>Using jsp:useBean action with a JavaBean</p>
    <jsp:useBean id="courseWelcomeBean" class="com.baeldung.boot.jsp.coursewelcome.CourseWelcomeBean"/>
    <jsp:setProperty name="courseWelcomeBean" property="username" value="Eric"/>
    <jsp:setProperty name="courseWelcomeBean" property="courseName" value="Spring Security"/>
    <div><%= courseWelcomeBean.greetingUser()%></div>
    <div><%= courseWelcomeBean.welcomeMsg()%></div>
</body>
</html>