<%@ page import="java.util.Random" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<html>
<head>
    <c:set value="JSTL XML Tags Example" var="pageTitle"/>
    <title>
        <c:out value="${pageTitle}"/>
    </title>
</head>
<body>
<h1>
    <c:out value="${pageTitle}"/>
</h1>
<c:remove var="pageTitle"/>
<p><c:out value="${pageTitle}"/></p>
<div>
    <h3>
        <c:out value="<x:transform>, <x::param>, <x:parse>, <x:set>, <x:out>, and <x:if> Examples"/>
    </h3>
    <h3>Store Items:</h3>
    <c:set var="xmltext">
        <items>
            <item>
                <name>Steve Madden</name>
                <category>Sneakers</category>
                <price>34</price>
            </item>

            <item>
                <name>Pearl Izumi</name>
                <category>Sneakers</category>
                <price>80</price>
            </item>

            <item>
                <name>Katy Perry</name>
                <category>Heels</category>
                <price>72</price>
            </item>
        </items>
    </c:set>

    <x:parse xml="${xmltext}" var="output"/>
    <b>The name of the first item listed in the store is</b>:
    <x:out select="$output/items/item[1]/name"/> with price $<x:out select="$output/items/item[1]/price"/>
    <br>

    <x:set var="fragment" select="$output//item"/>
    <b>The second item is </b>:
    <c:out value="${fragment}"/>

    <x:if select="$output//item">
    Document has at least one
    <item> element.
        </x:if>
        <br/>

        <c:import url="/items_xml" var="xslt"/>
        <x:transform xml="${xmltext}" xslt="${xslt}">
            <x:param name="bgColor" value="blue"/>
        </x:transform>
</div>
<div>

    <h3>
        <c:out value="<x:forEach> Example"/>
    </h3>
    <ul class="items">
        <x:forEach select="$output/items/item/name" var="item">
            <li>Item Name: <x:out select="$item"/></li>
        </x:forEach>
    </ul>
</div>
<div>
    <h3>
        <c:out value="<x:choose>, <x:when> and <x:otherwise> Example"/>
    </h3>

    <x:choose>
        <x:when select="$output//item/category = 'Sneakers'">
            Item category is Sneakers
        </x:when>

        <x:when select="$output//item/category = 'Heels'">
            Item category is Heels
        </x:when>

        <x:otherwise>
            Unknown category.
        </x:otherwise>
    </x:choose>
</div>

</body>
</html>