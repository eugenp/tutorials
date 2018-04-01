<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/util.js"></script>
</head>
<body>
<h1></h1>
<form:form method="POST" commandName="user" action="${pageContext.request.contextPath}/users/2" >
<table>
<tbody>
<tr>
<td><form:input path="username" cssClass="username-input"/></td>
<td><form:errors path="username" cssStyle="color: red;" cssClass="error-username"/></td>
</tr>
<tr>
<td><input class="form-btn" type="button" value="Edit" /></td>
<td></td>
<td></td>
</tr>
</tbody>
</table>
</form:form>
<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
