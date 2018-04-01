<%@ page import="i18n.LocaleSupport, java.util.Locale"%>
<%@ page session="true" %>

<%
    String language = request.getParameter("lang");
    Locale userLocale = new Locale(language);
    session.setAttribute(LocaleSupport.USER_PREFERRED_LOCALE, userLocale);
%>

<html>
The user's locale has been set appropriately.
</html>