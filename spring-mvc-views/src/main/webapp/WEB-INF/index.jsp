<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<spring:theme code='styleSheet'/>"/>
        <title>Themed Application</title>
    </head>
    <body>
        <header>
            <h1>Themed Application</h1>
            <hr />
        </header>
        <section>
            <h2>Spring MVC Theme Demo</h2>
            <security:authorize access="isAuthenticated()">
                <h4>User : <security:authentication property="principal.username" /></h4>
            </security:authorize>
            <form action="<c:url value='/'/>" method="POST" name="themeChangeForm" id="themeChangeForm">
                <div>
                    <h4>
                        Change Theme
                    </h4>
                </div>
                <select id="theme" name="theme" onChange="submitForm()">
                    <option value="">Reset</option>
                    <option value="light">Light</option>
                    <option value="dark">Dark</option>
                </select>
            </form>
        </section>

        <section>
            <form action="<c:url value='/logout.do'/>" method="POST" name="logoutForm">
                <button type="submit" name="submit">Sign Out</button>
            </form>
        </section>

        <script>
            function submitForm() {
              document.themeChangeForm.submit();
            }
        </script>
    </body>
</html>
