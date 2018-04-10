<%@ page import="java.util.Random" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <c:set value="JSTL SQL Tags Example" var="pageTitle"/>
    <title>
        <c:out value="${pageTitle}"/>
    </title>
</head>
<body>
<h1>
    <c:out value="${pageTitle}"/>
</h1>
<!--
    sql:setDataSource Example
-->
<sql:setDataSource var="dataSource" driver="com.mysql.cj.jdbc.Driver"
                   url="jdbc:mysql://localhost/test"
                   user="root" password=""/>

<div>

    <h3>
        <c:out value="<sql:query> Example"/>
    </h3>
    <sql:query dataSource="${dataSource}" var="result">
        SELECT * from USERS;
    </sql:query>
    <table border="1" width="50%">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Registered</th>
        </tr>

        <c:forEach var="user" items="${result.rows}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.index + 1}"/></td>
                <td><c:out value="${user.first_name}"/></td>
                <td><c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registered}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    <h3>
        <c:out value="<sql:update> Example"/>
    </h3>

    <sql:update dataSource="${dataSource}" var="count">
        INSERT INTO USERS(first_name, last_name, email) VALUES ('Grace', 'Adams', 'gracea@domain.com');
    </sql:update>
    <sql:query dataSource="${dataSource}" var="result">
        SELECT * from USERS;
    </sql:query>
    <table border="1" width="50%">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Registered</th>
        </tr>

        <c:forEach var="user" items="${result.rows}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.index + 1}"/></td>
                <td><c:out value="${user.first_name}"/></td>
                <td><c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registered}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>

    <h3>
        <c:out value="<sql:param> Example"/>
    </h3>

    <sql:update dataSource = "${dataSource}" var = "count">
        DELETE FROM USERS WHERE email = ?
        <sql:param value = "gracea@domain.com" />
    </sql:update>
    <sql:query dataSource="${dataSource}" var="result">
        SELECT * from USERS;
    </sql:query>
    <table border="1" width="50%">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Registered</th>
        </tr>

        <c:forEach var="user" items="${result.rows}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.index + 1}"/></td>
                <td><c:out value="${user.first_name}"/></td>
                <td><c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registered}"/></td>
            </tr>
        </c:forEach>
    </table>

</div>
<div>

    <h3>
        <c:out value="<sql:dateParam> Example"/>
    </h3>
    <%
        Date registered = new Date("2018/03/31");
        String email = "patrick@baeldung.com";
    %>

    <sql:update dataSource = "${dataSource}" var = "count">
        UPDATE Users SET registered = ? WHERE email = ?
        <sql:dateParam value = "<%=registered%>" type = "DATE" />
        <sql:param value = "<%=email%>" />
    </sql:update>
    <sql:query dataSource="${dataSource}" var="result">
        SELECT * from USERS;
    </sql:query>
    <table border="1" width="50%">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Registered</th>
        </tr>

        <c:forEach var="user" items="${result.rows}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.index + 1}"/></td>
                <td><c:out value="${user.first_name}"/></td>
                <td><c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registered}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>

    <h3>
        <c:out value="<sql:transaction> Example"/>
    </h3>
    <sql:transaction dataSource = "${dataSource}">
        <sql:update var = "count">
            UPDATE Users SET first_name = 'Patrick-Ellis' WHERE email='patrick@baeldung.com'
        </sql:update>

        <sql:update var = "count">
            UPDATE Users SET last_name = 'Nelson' WHERE email = 'patrick@baeldung.com'
        </sql:update>

        <sql:update var = "count">
            INSERT INTO Users(first_name, last_name, email) VALUES ('Grace', 'Adams', 'gracea@domain.com');
        </sql:update>
    </sql:transaction>
    <sql:query dataSource="${dataSource}" var="result">
        SELECT * from USERS;
    </sql:query>
    <table border="1" width="50%">
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Registered</th>
        </tr>

        <c:forEach var="user" items="${result.rows}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.index + 1}"/></td>
                <td><c:out value="${user.first_name}"/></td>
                <td><c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registered}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>