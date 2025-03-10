<%@ page import="java.util.*"%>
<html>
<head>
    <title>3rd party Module</title>
</head>
<body>
    <%
        String localeStr = request.getParameter("locale");
        Locale currentLocale = (localeStr != null ? new Locale(localeStr) : null);
    %>
    The language you have selected: <%=currentLocale != null ? currentLocale.getDisplayLanguage(currentLocale) : " None"%>
</body>
</html>