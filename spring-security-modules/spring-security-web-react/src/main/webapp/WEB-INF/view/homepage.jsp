<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <script src="csrf.js"></script>
</head>

<body>
    <h1>This is the body of the sample view</h1>

    <div>
        <h2>CSRF Testing</h2>
        <div>
            <span>CSRF Token: </span>
            <span id="csrf-token"></span>
            <input type="checkbox" id="include-csrf" name="include-csrf" checked /><label for="include-csrf">Include token</label>
        </div>
        <br/>
        <div>
            <button type="button" onclick="test('GET')">Test GET Request</button>&nbsp;
            <button type="button" onclick="test('POST')">Test POST Request</button>
        </div>
        <br/>
        <div><span>Request Result: </span><span id="csrf-result"></span></div><br/>
    </div>

    <h2>Roles</h2>
    <security:authorize access="hasRole('ROLE_USER')">
        This text is only visible to a user
        <br/> <br/>
        <a href="<c:url value="/admin/adminpage.html" />">Restricted Admin Page</a>
        <br/> <br/>
    </security:authorize>
	
    <security:authorize access="hasRole('ROLE_ADMIN')">
        This text is only visible to an admin
        <br/>
        <a href="<c:url value="/admin/adminpage.html" />">Admin Page</a>
        <br/>
    </security:authorize>

    <a href="<c:url value="/perform_logout" />">Logout</a>

    <script language="javascript">

        function test(method) {
            const includeCsrfCheckbox = document.querySelector('#include-csrf');
            const csrfResultDiv = document.querySelector('#csrf-result');
            const request = includeCsrfCheckbox.checked === true ? csrfRequest(method) : noCsrfRequest(method);
            request
                .then((res) => csrfResultDiv.innerText = res.ok ? method + ' Success' : method + ' Failure: ' + res.status)
                .catch((err) => csrfResultDiv.innerText = method + ' Failure: ' + err.toString());
        }

        function csrfRequest(method) {
            return fetch('/rest', { headers: { 'X-XSRF-TOKEN': window.getCsrfToken() }, method });
        }

        function noCsrfRequest(method) {
            return fetch('/rest', { method });
        }

        document.querySelector('#csrf-token').innerText = window.getCsrfToken();
    </script>
</body>
</html>