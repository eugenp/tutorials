<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="false"%>
<c:if test="${param.error != null}">
	<div id="error">
		<spring:message code="message.badCredentials"></spring:message>
	</div>
</c:if>
<c:if test="${param.regSucc == 1}">
		<div id="error">
			<spring:message code="message.regSucc"></spring:message>
		</div>
</c:if>
<c:if test="${param.regError == 1}">

	<div id="error">
		<spring:message code="message.regError"></spring:message>
	</div>
	<a href="registration.html">Register</a>
</c:if>
<fmt:message key="message.password" var="noPass" />
<fmt:message key="message.username" var="noUser" />
<html>
<head>

<script type="text/javascript">
	function validate() {
		if (document.f.j_username.value == ""
				&& document.f.j_password.value == "") {
			alert("${noUser} & ${noPass}");
			document.f.j_username.focus();
			return false;
		}
		if (document.f.j_username.value == "") {
			alert("${noUser}");
			document.f.j_username.focus();
			return false;
		}

		if (document.f.j_password.value == "") {
			alert("${noPass}");
			document.f.j_password.focus();
			return false;
		}
	}
</script>
</head>

<body>
	<h1>Login</h1>
	<a href="?lang=en">English</a> |
	<a href="?lang=es_ES">Spanish</a>
		<form name='f' action="j_spring_security_check" method='POST'
			onsubmit="return validate();">

			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='j_username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='j_password' /></td>
				</tr>
				<tr>
					<td><input name="submit" type="submit" value="submit" /></td>
				</tr>
			</table>

		</form>
	<br> Current Locale : ${pageContext.response.locale}
	
	<a href="<c:url value="/registration.html" />">Home</a>
</body>
</html>