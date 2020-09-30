<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cars</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Car Info:</h1>
${test}
<c:out value="${test}"/>
<ul>
    <li>VIN: <c:out value="${car.vin}"/></li>
    <li>Brand: <c:out value="${car.brand}"/></li>
    <li>Model: <c:out value="${car.model}"/></li>
    ${test}
    <li>Test: <c:out value="${test}"/></li>
    ...
</ul>
</body>
</html>