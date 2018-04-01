<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Shop List page</title>
</head>
<body>
<h1>Shop List page</h1>
<table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="25px">id</th><th width="150px">company</th><th width="25px">employees</th><th width="50px">actions</th>
</tr>
</thead>
<tbody>
<c:forEach var="shop" items="${shopList}">
<tr>
<td>${shop.id}</td>
<td>${shop.name}</td>
<td>${shop.emplNumber}</td>
<td>
<a href="${pageContext.request.contextPath}/shop/edit/${shop.id}.html">Edit</a><br/>
<a href="${pageContext.request.contextPath}/shop/delete/${shop.id}.html">Delete</a><br/>
</td>
</tr>
</c:forEach>
</tbody>
</table>
<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>