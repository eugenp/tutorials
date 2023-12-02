<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String jspTag = "<h1>Hello</h1>";
%>
<html>
    <head>
        <title>Convert to an HTML tag</title>
        <script type="text/javascript">
            function printTag() {
                var tags = document.getElementById("fromJspTag").innerHTML;
                console.info(tags);
            }
        </script>
    </head>
    <body onLoad="printTag()">
       <div id="fromJspTag"><%=jspTag%></div>
       <div>Open the browser console to see the tags.</div>
    </body>
</html>