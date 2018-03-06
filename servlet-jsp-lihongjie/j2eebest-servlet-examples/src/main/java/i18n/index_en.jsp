<%@ page import="java.util.Locale, com.ora.i18n.LocaleSupport" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ taglib uri="/oreilly-taglib" prefix="oreillytags"%>
<%
    Locale userLocale= new Locale("en");
    session.setAttribute(LocaleSupport.USER_PREFERRED_LOCALE, userLocale);
%>
    
<html>
    <frameset rows="20%, 80%">
        <frame src="menu_bar.jsp">
            <frameset cols="30%, 70%">
                <frame src="navigation_bar.jsp">
                    <frame src="content.jsp">
            </frameset>
    </frameset>
</html>