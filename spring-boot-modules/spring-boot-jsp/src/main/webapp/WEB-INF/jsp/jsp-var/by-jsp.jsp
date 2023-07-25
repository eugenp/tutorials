<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String jspMsg = StringEscapeUtils.escapeEcmaScript("Hello! This is Sam's page.");
%>
<html>
    <head>
        <title>Conversion by JSP expression tag</title>
            var jsMsg = '<%=jspMsg%>';
            console.info(jsMsg);
        </script>
    </head>
    <body>
       <div>Open the browser console to see the message.</div>
    </body>
</html>