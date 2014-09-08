<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>
<fmt:message key="message.password" var="noPass" />
<fmt:message key="message.username" var="noUser" />
<html>

<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<c:if test="${param.error != null}">
	<div class="alert alert-error">
		<spring:message code="message.badCredentials"></spring:message>
	</div>
</c:if>
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
	<h1><spring:message code="label.form.loginTitle"></spring:message></h1>
	<a href="?lang=en"><spring:message code="label.form.loginEnglish"></spring:message></a> |
	<a href="?lang=es_ES"><spring:message code="label.form.loginSpanish"></spring:message></a>
	<form name='f' action="j_spring_security_check" method='POST' onsubmit="return validate();">
		<table>
			<tr>
				<td><label><spring:message code="label.form.loginEmail"></spring:message></label></td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td><label><spring:message code="label.form.loginPass"></spring:message></label></td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td><input name="submit" type="submit" value=<spring:message code="label.form.submit"></spring:message> /></td>
			</tr>
		</table>

	</form>
	<br> Current Locale : ${pageContext.response.locale}
	<br>
	<a href="<c:url value="/user/registration" />"><spring:message code="label.form.loginSignUp"></spring:message></a>
</body>

</html>