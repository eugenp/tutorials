<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Spring Session with Servlet</title>
</head>
<body>
    <h3>Add Session Attribute</h3>
		<form action="./home" method="POST">
		    Attribute Name :  <input type="text" name="attributeName"/><br/>
		    Attribute Value : <input type="text" name="attributeValue"/><br/><br/>
			<input type="submit" value="Add Attribute"/>
		</form>
		<c:if test="${sessionScope.size() gt 0}">
		  <h3>Session Attribute Details</h3>
		  <table>
			<tr>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
			</tr>
			<c:forEach items="${sessionScope}" var="attb">
				<tr>
					<td><c:out value="${attb.key}"/></td>
					<td><c:out value="${attb.value}"/></td>
				</tr>
			</c:forEach>
		  </table>
        </c:if>
</body>
</html>