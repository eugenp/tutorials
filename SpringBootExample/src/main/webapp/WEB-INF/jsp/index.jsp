<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="ISO-8859-1">
<title>Get Form</title> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
 <h2>Spring MVC Form Validation Demo - Login Form</h2>
        <table border="0" width="90%">
        <form:form action="/NPCISimulator/sendRequest" modelAttribute="payerForm">
                <tr>
                    <td align="left" width="20%">Email: </td>
                    <td align="left" width="40%"><form:input path="name" size="30"/></td>
              
                </tr>
                <tr>
                    <td>Address: </td>
                    <td><form:input path="address" size="30"/></td>
        
                </tr>
                                 <tr>
                    <td>Amount</td>
                    <td><form:input path="amount" size="30"/></td>
        <%--             <td><form:errors path="amount" cssClass="error"/></td> --%>
                </tr>
                 <tr>
                    <td>Currency: </td>
                    <td><form:input path="currency" size="30"/></td>
               <%--      <td><form:errors path="currency" cssClass="error"/></td> --%>
                </tr>
               
                <tr>
                    <td></td>
                    <td align="center"><input type="submit" value="Login"/></td>
                    <td></td>
                </tr>
             
        </form:form>
        </table>
</body>
</html>