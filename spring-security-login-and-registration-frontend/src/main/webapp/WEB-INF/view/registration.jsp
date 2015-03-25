<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><spring:message code="label.form.title"></spring:message></title>
</head>
<body>
    <div class="container">
        <div >
            <h1>
                <spring:message code="label.form.title"></spring:message>
            </h1>
            <form action="/" method="POST" enctype="utf8">
                <div class="form-group row" >
                    <label class="col-sm-3"><spring:message code="label.user.firstName"></spring:message></label>
                    <span class="col-sm-5"><input class="form-control" name="firstName" value="" /></span>
                    <span id="firstNameError" class="alert alert-danger col-sm-4" style="display:none"></span>
                    
                </div>
                <div class="form-group row">
                    <label class="col-sm-3"><spring:message code="label.user.lastName"></spring:message></label>
                    <span class="col-sm-5"><input class="form-control" name="lastName" value="" /></span>
                    <span id="lastNameError" class="alert alert-danger col-sm-4" style="display:none"></span>
                    
                </div>
                <div class="form-group row">
                    <label class="col-sm-3"><spring:message code="label.user.email"></spring:message></label>
                    <span class="col-sm-5"><input class="form-control" name="email" value="" /></span>                    
                    <span id="emailError" class="alert alert-danger col-sm-4" style="display:none"></span>
                    
                </div>
                <div class="form-group row">
                    <label class="col-sm-3"><spring:message code="label.user.password"></spring:message></label>
                    <span class="col-sm-5"><input class="form-control" name="password" value="" type="password" /></span>
                    <span id="passwordError" class="alert alert-danger col-sm-4" style="display:none"></span>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3"><spring:message code="label.user.confirmPass"></spring:message></label>
                    <span class="col-sm-5"><input class="form-control" name="matchingPassword" value="" type="password" /></span>
                    <span id="globalError" class="alert alert-danger col-sm-4" style="display:none"></span>
                </div>
                <br>
                <a href="#" class="btn btn-primary" onclick="register()">
                    <spring:message code="label.form.submit"></spring:message>
                </a>
            </form>
            <br> 
            <a href="<c:url value="login.html" />"><spring:message code="label.form.loginLink"></spring:message></a>
        </div>
    </div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function register(){
	$(".alert").html("").hide();
    var formData= $('form').serialize();
    $.post("/spring-security-login-and-registration/user/registration",formData ,function(data){
        if(data.message == "success"){
        	window.location.href = "<c:url value="/successRegister.html"></c:url>";
        }
        
       
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = "<c:url value="/emailError.html"></c:url>";
        }
        else if(data.responseJSON.error == "UserAlreadyExist"){
            $("#emailError").show().html(data.responseJSON.message);
        }
        else if(data.responseJSON.error.indexOf("InternalError") > -1){
            window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + data.responseJSON.message;
        }
        else{
            var errors = $.parseJSON(data.responseJSON.message);
            $.each( errors, function( index,item ){
                $("#"+item.field+"Error").show().html(item.defaultMessage);
            });
            errors = $.parseJSON(data.responseJSON.error);
            $.each( errors, function( index,item ){
                $("#globalError").show().append(item.defaultMessage+"<br>");
            });
        }
    });
}
</script>
</body>

</html>