<%@ page import="java.util.Locale, com.ora.i18n.LocaleSupport"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ taglib uri="/oreilly-taglib" prefix="oreillytags"%>
<%
    Locale userLocale= new Locale("iw");
    session.setAttribute(LocaleSupport.USER_PREFERRED_LOCALE, userLocale);
%>

<html>
    <frameset rows="20%, 80%">
        <frame src="menu_bar.jsp">
            <frameset cols="70%, 30%">
                <frame src="content.jsp">
                    <frame src="navigation_bar.jsp">
            </frameset>
    </frameset>
</html>
