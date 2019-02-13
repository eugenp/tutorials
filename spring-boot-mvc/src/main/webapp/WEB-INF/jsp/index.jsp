<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<title>Access Spring MVC params</title>
<script src="/js/jquery.js"></script>	
<script src="/js/script-async.js"></script>
<script src="/js/script-async-jquery.js"></script>
<script>
	var number = <c:out value="${number}"></c:out>;
	var message = "<c:out value="${message}"></c:out>";
</script>
</head>
<body>
	<h2>Data from the external JS file (due to loading order)</h2>
	<div id="number-ext"></div>
	<div id="message-ext"></div>
	<h2>Asynchronous loading from external JS file (plain JS)</h2>
	<div id="number-async"></div>
	<div id="message-async"></div>
	<h2>Asynchronous loading from external JS file (jQuery)</h2>
	<div id="number-async-jquery"></div>
	<div id="message-async-jquery"></div>

</body>
<script src="/js/script.js"></script>
</html>