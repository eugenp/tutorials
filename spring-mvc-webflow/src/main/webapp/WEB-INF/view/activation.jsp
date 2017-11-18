<html>
<body>
<h2>Hello World!</h2>
<%--<a href="${flowExecutionUrl}">Start</a>--%>
<%--<input type="submit" name="_eventId_success" value="Proceed" />--%>
<%--<input type="submit" name="_eventId_failure" value="Cancel" />--%>

<form method="post" action="${flowExecutionUrl}">

    <input type="hidden" name="_eventId" value="activate">
    <input type="submit" value="Proceed" />

</form>

<form method="post" action="${flowExecutionUrl}">

    <input type="hidden" name="_eventId" value="cancel">
    <input type="submit" value="Cancel" />

</form>

</body>
</html>
