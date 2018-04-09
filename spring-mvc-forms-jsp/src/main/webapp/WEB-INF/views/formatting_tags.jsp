<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <c:set value="JSTL Formatting Tags Example" var="pageTitle"/>
    <title>
        <c:out value="${pageTitle}"/>
    </title>
</head>
<body>
<h1>
    <c:out value="${pageTitle}"/>
</h1>
<div>
    <h3>
        <c:out value="<fmt:formatNumber> Example"/>
    </h3>
    <c:set var="fee" value="35050.1067"/>

    <p>Formatted Number - Currency: <fmt:formatNumber value="${fee}"
                                                      type="currency"/></p>

    <p>Formatted Number - Maximum Integer Digits: <fmt:formatNumber type="number"
                                                                    maxIntegerDigits="2" value="${fee}"/></p>

    <p>Formatted Number - Maximum Fraction Digits: <fmt:formatNumber type="number"
                                                                     maxFractionDigits="2" value="${fee}"/></p>

    <p>Formatted Number - Grouping: <fmt:formatNumber type="number"
                                                      groupingUsed="false" value="${fee}"/></p>

    <p>Formatted Number - Percent with Maximum Integer Digits: <fmt:formatNumber type="percent"
                                                                                 maxIntegerDigits="3"
                                                                                 value="${fee}"/></p>

    <p>Formatted Number - Percent with Minimum Fraction Digits: <fmt:formatNumber type="percent"
                                                                                  minFractionDigits="10"
                                                                                  value="${fee}"/></p>

    <p>Formatted Number Percent with Minimum Integer Digits: <fmt:formatNumber type="percent"
                                                                               minIntegerDigits="4" value="${fee}"/></p>

    <p>Formatted Number - with Pattern: <fmt:formatNumber type="number"
                                                          pattern="###.##E0" value="${fee}"/></p>

    <p>Formatted Number - Currency with Internationalization :
        <fmt:setLocale value="en_US"/>
        <fmt:formatNumber value="${fee}" type="currency"/>
    </p>
</div>
<div>

    <h3>
        <c:out value="<fmt:parseNumber> Example"/>
    </h3>
    <fmt:parseNumber var="i" type="number" value="${fee}"/>
    <p>Parsed Number : <c:out value="${i}"/></p>
    <fmt:parseNumber var="i" integerOnly="true"
                     type="number" value="${fee}"/>
    <p>Parsed Number - with Integer Only set to True: <c:out value="${i}"/></p>
</div>
<div>
    <h3>
        <c:out value="<fmt:formatDate> Example"/>
        <c:set var="now" value="<%= new java.util.Date()%>"/>
    </h3>

    <p>Formatted Date - with type set to time: <fmt:formatDate type="time"
                                       value="${now}"/></p>

    <p>Formatted Date - with type set to date: <fmt:formatDate type="date"
                                                               value="${now}"/></p>

    <p>Formatted Date - with type set to both: <fmt:formatDate type="both"
                                                               value="${now}"/></p>

    <p>Formatted Date - with short style for both date and time: <fmt:formatDate type="both"
                                                                                 dateStyle="short" timeStyle="short"
                                                                                 value="${now}"/></p>

    <p>Formatted Date - with medium style for both date and time: <fmt:formatDate type="both"
                                                                                  dateStyle="medium" timeStyle="medium"
                                                                                  value="${now}"/></p>

    <p>Formatted Date - with long style for both date and time: <fmt:formatDate type="both"
                                                                                dateStyle="long" timeStyle="long"
                                                                                value="${now}"/></p>

    <p>Formatted Date - with pattern: <fmt:formatDate pattern="yyyy-MM-dd"
                                                      value="${now}"/></p>
</div>
<div>

    <h3>
        <c:out value="<fmt:parseDate> Example"/>
    </h3>

    <c:set var="today" value="28-03-2018"/>
    <fmt:parseDate value="${today}" var="parsedDate" pattern="dd-MM-yyyy"/>
    <p>Parsed Date: <c:out value="${parsedDate}"/></p>
</div>
<div>

    <h3>
        <c:out value="<fmt:bundle> and <fmt:message> Example"/>
    </h3>

    <p>
        <fmt:bundle basename="com.baeldung.jstl.bundles.CustomMessage" prefix="verb.">
            <fmt:message key="go"/><br/>
            <fmt:message key="come"/><br/>
            <fmt:message key="sit"/><br/>
            <fmt:message key="stand"/><br/>
        </fmt:bundle>
    </p>
</div>
<div>

    <h3>
        <c:out value="<fmt:setLocale> Example"/>
    </h3>

    <p>
        <fmt:setLocale value="fr_FR"/>
        <fmt:bundle basename="com.baeldung.jstl.bundles.CustomMessage" prefix="verb.">
            <fmt:message key="go"/><br/>
            <fmt:message key="come"/><br/>
            <fmt:message key="sit"/><br/>
            <fmt:message key="stand"/><br/>
        </fmt:bundle>
    </p>
</div>
<div>

    <h3>
        <c:out value="<fmt:setBundle> Example"/>
    </h3>

    <p>
        <fmt:setLocale value="En"/>
        <fmt:setBundle basename="com.baeldung.jstl.bundles.CustomMessage" var="lang"/>
        <fmt:message key="verb.go" bundle="${lang}"/><br/>
        <fmt:message key="verb.come" bundle="${lang}"/><br/>
        <fmt:message key="verb.sit" bundle="${lang}"/><br/>
        <fmt:message key="verb.stand" bundle="${lang}"/><br/>
    </p>
</div>
<div>

    <h3>
        <c:out value="<fmt:timeZone> Example"/>
    </h3>
    <table border="1" width="40%">
        <tr>
            <td width="100%" colspan="2">
                <p align="center">
                    <b>
                        <font size="4">Time being formatted:
                            <fmt:formatDate value="${now}" type="both"
                                            timeStyle="long" dateStyle="long"/>
                        </font>
                    </b>
                </p>
            </td>
        </tr>

        <c:forEach var="zone"
                   items="<%= java.util.TimeZone.getAvailableIDs()%>" end="4">
            <tr>
                <td width="51%">
                    <c:out value="${zone}"/>
                </td>
                <td width="49%">
                    <fmt:timeZone value="${zone}">
                        <fmt:formatDate value="${now}" timeZone="${zn}"
                                        type="both"/>
                    </fmt:timeZone>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div>

    <h3>
        <c:out value="<fmt:setTimeZone> Example"/>
    </h3>

    <p>Current Date with Default Time Zone: <fmt:formatDate value="${now}"
                                                            type="both" timeStyle="long" dateStyle="long"/></p>
    <p>Change Time Zone to GMT+9</p>
    <fmt:setTimeZone value="GMT+9"/>
    <p>Date in Changed Zone: <fmt:formatDate value="${now}"
                                             type="both" timeStyle="long" dateStyle="long"/></p>
</div>
<div>

    <h3>
        <c:out value="<fmt:requestEncoding> Example"/>
    </h3>
    <fmt:requestEncoding value = "UTF-8" />
    <fmt:setLocale value = "fr_FR"/>
    <fmt:setBundle basename = "com.baeldung.jstl.bundles.CustomMessage" var = "lang"/>
    <fmt:message key="verb.go" bundle="${lang}"/><br/>
    <fmt:message key="verb.come" bundle="${lang}"/><br/>
    <fmt:message key="verb.sit" bundle="${lang}"/><br/>
    <fmt:message key="verb.stand" bundle="${lang}"/><br/>
</div>
</body>
</html>
