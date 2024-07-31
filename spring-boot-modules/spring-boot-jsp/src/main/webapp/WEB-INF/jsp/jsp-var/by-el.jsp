<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String jspMsg = StringEscapeUtils.escapeEcmaScript("Hello! This is Sam's page.");
    request.setAttribute("jspMsg", jspMsg);
%>
<html>
    <head>
        <title>Conversion by JSP EL</title>
        <script type="text/javascript">
            var jsMsg = '${requestScope.jspMsg}';
            console.info(jsMsg);
        </script>
    </head>
    <body>
       <div>Open the browser console to see the message.</div>
    </body>
</html>