<%@ page import="java.util.Random" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"
           uri = "http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <c:set value="JSTL Function Tags Example" var="pageTitle"/>
    <title>
        <c:out value="${pageTitle}"/>
    </title>
</head>
<body>
<h1>
    <c:out value="${pageTitle}"/>
</h1>
<c:set var = "string1" value = "This is first string"/>
<c:set var = "string2" value = "This is second string with <tag>test XML</tag>"/>

<div>
    <h3>
        <c:out value="fn:contains() Example"/>
    </h3>
    <c:if test = "${fn:contains(string1, 'first')}">
    <p>Found 'first' in string<p>
    </c:if>
</div>
<div>
    <h3>
        <c:out value="fn:containsIgnoreCase() Example"/>
    </h3>
    <c:if test = "${fn:containsIgnoreCase(string1, 'first')}">
    <p>Found 'first' string<p>
    </c:if>

    <c:if test = "${fn:containsIgnoreCase(string1, 'FIRST')}">
    <p>Found 'FIRST' string<p>
    </c:if>
</div>
<div>
    <h3>
        <c:out value="fn:endsWith() Example"/>
    </h3>
    <c:if test = "${fn:endsWith(string1, 'string')}">
    <p>String ends with 'string'<p>
    </c:if>
</div>
<div>
    <h3>
        <c:out value="fn:escapeXml() Example"/>
    </h3>
    <p>With escapeXml() Function:</p>
    <p>string (1) : ${fn:escapeXml(string1)}</p>
    <p>string (2) : ${fn:escapeXml(string2)}</p>

    <p>Without escapeXml() Function:</p>
    <p>string (1) : ${string1}</p>
    <p>string (2) : ${string2}</p>
</div>
<div>
    <h3>
        <c:out value="fn:indexOf() Example"/>
    </h3>
    <p>Index (1) : ${fn:indexOf(string1, "first")}</p>
    <p>Index (2) : ${fn:indexOf(string2, "second")}</p>
</div>
<div>
    <h3>
        <c:out value="fn:join() and fn:split() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:split(string1, ' ')}" />
    <c:set var = "string4" value = "${fn:join(string3, '-')}" />
    <p>Final String : ${string4}</p>
</div>
<div>
    <h3>
        <c:out value="fn:length() Example"/>
    </h3>
    <p>Length of String (1) : ${fn:length(string1)}</p>
    <p>Length of String (2) : ${fn:length(string2)}</p>
</div>
<div>
    <h3>
        <c:out value="fn:replace() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:replace(string1, 'first', 'third')}" />
    <p>Final String : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:startsWith() Example"/>
    </h3>
    <c:if test = "${fn:startsWith(string1, 'This')}">
        <p>String starts with 'This'</p>
    </c:if>
</div>
<div>
    <h3>
        <c:out value="fn:substring() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:substring(string1, 5, 15)}" />

    <p>Final sub string : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:substringAfter() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:substringAfter(string1, 'is')}" />

    <p>Final sub string : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:substringBefore() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:substringBefore(string1, 'is')}" />

    <p>Final sub string : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:toLowerCase() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:toLowerCase(string1)}" />

    <p>Final string : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:toUpperCase() Example"/>
    </h3>
    <c:set var = "string3" value = "${fn:toUpperCase(string1)}" />

    <p>Final string : ${string3}</p>
</div>
<div>
    <h3>
        <c:out value="fn:trim() Example"/>
    </h3>
    <c:set var = "string1" value = "This is first String    "/>
    <p>String (1) Length : ${fn:length(string1)}</p>

    <c:set var = "string2" value = "${fn:trim(string1)}" />
    <p>String (2) Length : ${fn:length(string2)}</p>
    <p>Final string : "${string2}"</p>
</div>
</body>
</html>