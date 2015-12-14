<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>
<fmt:message key="message.password" var="noPass" />
<fmt:message key="message.username" var="noUser" />

<html>

<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<title><spring:message code="label.pages.home.title"></spring:message></title>
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
<style type="text/css">
.wrapper{width:500px;margin-left:auto;margin-right:auto}
label{padding-left:0 !important}
</style>
</head>
<body>
<c:if test="${param.message != null}">
<div class="alert alert-info">
${param.message}
</div>
</c:if>


<c:if test="${param.error != null}">
<div class="alert alert-danger">
${SPRING_SECURITY_LAST_EXCEPTION}
</div>
</c:if>

    <div class="container">
        <div class="row wrapper">
            <h1>
                <spring:message code="label.form.loginTitle"></spring:message>
            </h1>
            <a href="?lang=en"><spring:message code="label.form.loginEnglish"></spring:message></a>
            | <a href="?lang=es_ES"><spring:message
                    code="label.form.loginSpanish"></spring:message></a>
            <br><br>
            <form name='f' action="j_spring_security_check" method='POST'
                onsubmit="return validate();">
                
                <label class="col-sm-4"><spring:message code="label.form.loginEmail"></spring:message></label>
                <span class="col-sm-8"><input class="form-control" type='text' name='j_username' value=''></span>
                
                <br><br>        
                <label class="col-sm-4"><spring:message code="label.form.loginPass"></spring:message></label>
                <span class="col-sm-8"><input class="form-control" type='password' name='j_password' /></span>
                
                <br><br>
                <input class="btn btn-primary" name="submit" type="submit"
                            value=<spring:message code="label.form.submit"></spring:message> />

            </form>
            <br> Current Locale : ${pageContext.response.locale} <br><br> 
            <a class="btn btn-default" href="<c:url value="registration.html" />"><spring:message
                    code="label.form.loginSignUp"></spring:message></a>
            <br><br>
            <a class="btn btn-default" href="<c:url value="/forgetPassword.html" />"><spring:message
              code="message.resetPassword"></spring:message></a>
        </div>
    </div>
</body>

</html>