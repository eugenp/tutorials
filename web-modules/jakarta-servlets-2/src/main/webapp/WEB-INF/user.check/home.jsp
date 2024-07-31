<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<html>
    <head>
        <title>login success - current session info</title>
    </head>
    <body>
        <section>
            <h2>user info</h2>
            <div>
                <span>name: ${user.name}</span>
            </div>
    
            <div>
                <span>logins:</span>
                <ul>
                    <c:forEach var="login" items="${user.logins}">
                        <li>${login}</li>
                    </c:forEach>
                </ul>
            </div>
    
            <div>
                <a href="${pageContext.request.contextPath}/user-check/logout">
                    logout
                </a>
            </div>
        </section>
    </body>
</html>
