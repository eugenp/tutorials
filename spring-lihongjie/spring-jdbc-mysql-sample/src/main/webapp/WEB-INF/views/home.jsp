<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Hello Spring MySQL</title>
</head>
<body>
	<h1>Hello Spring MySQL!</h1>

	<h4>Database Info:</h4>
	DataSource: <c:out value="${dbinfo}"/></br>

	<h4>Current States:</h4>
	<c:if test="${not empty states}">
		<p>
			<c:forEach var="state" items="${states}">
				<c:out value="${state}"/></br>
			</c:forEach>
		</p>
	</c:if>
	<c:if test="${empty states}">
		<p>No States found</p>
	</c:if>
</body>
</html>
