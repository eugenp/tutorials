<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>
<body>

<h2>Submitted Customer Information</h2>
<table>
    <tr>
        <td>Customer Id :</td>
        <td>${customerId}</td>
    </tr>
    <tr>
        <td>Customer Name :</td>
        <td>${customerName}</td>
    </tr>
    <tr>
        <td>Customer Contact :</td>
        <td>${customerContact}</td>
    </tr>
    <tr>
        <td>Customer Email :</td>
        <td>${customerEmail}</td>
    </tr>
</table>
</body>
</html>