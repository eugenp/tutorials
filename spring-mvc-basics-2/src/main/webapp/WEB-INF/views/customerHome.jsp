<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Form Example - Add Customer</title>
    <style>
        .error {
            color: #ff0000;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h3>Welcome, Enter The Customer Details</h3>

<form:form method="POST" action="${pageContext.request.contextPath}/addCustomer" modelAttribute="customer">
    <table>
        <tr>
            <td><form:label path="customerId">Customer Id</form:label></td>
            <td><form:input path="customerId"/></td>
            <td><form:errors path="customerId" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="customerName">Customer Name</form:label></td>
            <td><form:input path="customerName"/></td>
            <td><form:errors path="customerName" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="customerContact">Customer Contact</form:label></td>
            <td><form:input path="customerContact"/></td>
            <td><form:errors path="customerContact" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="customerEmail">Customer Email</form:label></td>
            <td><form:input path="customerEmail"/></td>
            <td><form:errors path="customerEmail" cssClass="error"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

</body>

</html>