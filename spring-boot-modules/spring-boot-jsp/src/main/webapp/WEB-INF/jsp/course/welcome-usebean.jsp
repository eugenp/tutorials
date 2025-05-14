<html>
<head>
    <title>Welcome to Course</title>
</head>
<body>
    <p>Using jsp:useBean action</p>
    <jsp:useBean id="welcomeBean" class="com.baeldung.boot.jsp.coursewelcome.CourseWelcome"/>
    <div>
        <%= welcomeBean.greeting("Kevin")%>
    </div>
    <div>
        <%= com.baeldung.boot.jsp.coursewelcome.CourseWelcome.staticWelcome("Java Collections")%>
    </div>
</body>
</html>