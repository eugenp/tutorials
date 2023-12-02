<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String jspMsg = StringEscapeUtils.escapeEcmaScript("Hello! This is Sam's page.");
    request.setAttribute("scopedMsg", jspMsg);
%>
<html>
    <head>
        <title>Conversion by JSTL</title>
        <script type="text/javascript">
            var jsMsg = '<c:out value="${scopedMsg}" escapeXml="false"/>';
            console.info(jsMsg);
        </script>
    </head>
    <body>
       <div>Open the browser console to see the message.</div>
    </body>
</html>