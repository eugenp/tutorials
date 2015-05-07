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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="message.resetPassword"></spring:message></title>
</head>
<body>
<div class="container">
    <h1><spring:message code="message.resetPassword"></spring:message></h1>
    <br>
<div class="row">
        <label class="col-sm-1"><spring:message code="label.user.email"></spring:message></label>
        <span class="col-sm-5"><input class="form-control" id="email" name="email" type="email" value="" /></span>
        <button class="btn btn-primary" type="submit" onclick="resetPass()"><spring:message code="message.resetPassword"></spring:message></button>
</div>

<br> 
<a class="btn btn-default" href="<c:url value="registration.html" />"><spring:message code="label.form.loginSignUp"></spring:message></a>
<br><br>
<a class="btn btn-default" href="<c:url value="login.html" />"><spring:message code="label.form.loginLink"></spring:message></a>

</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function resetPass(){
    var email = $("#email").val();
    $.post("<c:url value="/user/resetPassword"></c:url>",{email: email} ,function(data){
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

$(document).ajaxStart(function() {
    $("title").html("LOADING ...");
});
</script>
</body>

</html>