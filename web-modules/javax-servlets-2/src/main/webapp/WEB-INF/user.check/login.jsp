<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<html>
    <head>
        <title>login</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/user-check/login"
          method="POST">
            <input type="hidden" name="origin" value="${origin}">
            <c:if test="${not empty origin}">
                <div>* redirected to login from: ${origin}</div>
            </c:if>
    
            <c:if test="${not empty error}">
                <div>* error: ${error}</div>
            </c:if>
    
            <fieldset>
                <legend>credentials</legend>
                
                <label for="name">name</label> 
                <input type="text" name="name">
    
                <label for="password">password</label> 
                <input type="password" name="password"> 
                
                <input type="submit">
            </fieldset>
        </form>
    </body>
</html>
