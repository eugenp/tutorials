<%@ page import="java.util.Random" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:set value="JSTL Core Tags Example" var="pageTitle"/>
    <title>
        <c:out value="${pageTitle}"/>
    </title>
</head>
<body>
<h1>
    <c:out value="${pageTitle}"/>
</h1>
<c:remove var="pageTitle"/>
<p><c:out value="${pageTitle}"/></p>
<div>
    <h3>
        <c:out value="<c:catch> Example"/>
    </h3>
    <c:catch var ="exceptionThrown">
        <% int x = Integer.valueOf("a");%>
    </c:catch>

    <c:if test = "${exceptionThrown != null}">
        <p>The exception is : ${exceptionThrown} <br />
            There is an exception: ${exceptionThrown.message}</p>
    </c:if>
</div>
<div>

    <h3>
        <c:out value="<c:choose> Example"/>
        <c:set value="<%= Calendar.getInstance().get(Calendar.SECOND)%>" var="seconds"/>
    </h3>
    <p>
        <c:choose>
            <c:when test="${seconds le 30 }">
                <c:out value="${seconds} is less than 30"/>
            </c:when>
            <c:when test="${seconds eq 30 }">
                <c:out value="${seconds} is equal to 30"/>
            </c:when>
            <c:otherwise>
                <c:out value="${seconds} is greater than 30"/>
            </c:otherwise>
        </c:choose>
    </p>
</div>
<div>
    <h3>
        <c:out value="<c:import> Example"/>
    </h3>

    <c:import var = "data" url = "http://www.example.com"/>
    <c:out value = "${data}"/>
</div>
<div>

    <h3>
        <c:out value="<c:forEach> Example"/>
    </h3>

    <c:forEach var = "i" items="1,4,5,6,7,8,9">
    Item <c:out value = "No. ${i}"/><p>
    </c:forEach>
</div>
<div>

    <h3>
        <c:out value="<c:forToken> Example"/>
    </h3>

    <c:forTokens items = "Patrick:Wilson:Ibrahima:Chris" delims = ":" var = "name">
    <c:out value = "Name: ${name}"/><p>
    </c:forTokens>
</div>
<div>

    <h3>
        <c:out value="<c:url> and <c:param> Example"/>
    </h3>
    <c:url value = "/core_tags" var = "myURL">
        <c:param name = "parameter_1" value = "1234"/>
        <c:param name = "parameter_2" value = "abcd"/>
    </c:url>
    <c:out value = "URL: ${myURL}"/>
</div>
</body>
</html>