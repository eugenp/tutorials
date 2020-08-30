<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello React!</title>
    <script type="text/javascript" src="js/react.js"></script>
    <script type="text/javascript" src="js/react-dom.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="root">${content}</div>
<script type="text/javascript" src="app.js"></script>
<script type="text/javascript">
	ReactDOM.render(
        React.createElement(App, {data: [0,1,1]}),
        document.getElementById("root")
    );
</script>
</body>
</html>