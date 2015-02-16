<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="message.resetPassword"></spring:message></title>
</head>
<body>
<div class="container">
<div class="span12">
<h1>
<spring:message code="message.resetPassword"></spring:message>
</h1>
<div>
<br>

<tr>
<td><label><spring:message code="label.user.email"></spring:message></label></td>
<td><input id="email" name="email" type="email" value="" /></td>
</tr>

<button type="submit" onclick="resetPass()">
<spring:message code="message.resetPassword"></spring:message>
</button>
</div>

<br> 
<a href="<c:url value="/user/registration" />"><spring:message code="label.form.loginSignUp"></spring:message></a>
<br>
<a href="<c:url value="login.html" />"><spring:message code="label.form.loginLink"></spring:message></a>

</div>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function resetPass(){
    var email = $("#email").val();
    $.post("<c:url value="/user/resetPassword2"></c:url>",{email: email} ,function(data){
            window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + data.message;
    })
    .fail(function(data) {
    	if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = "<c:url value="/emailError.html"></c:url>";
        }
        else{
            window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + data.responseJSON.message;
        }
    });
}
</script>
</body>

</html>