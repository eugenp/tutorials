<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demo - Using Map in JSP (Scriptlets)</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 3px;
        }
    </style>
</head>
<body>
<div>Movies in the Map Object (Using JSP Scriptlets)</div>
<br/>
<% Map<String, String> movieMap = (Map<String, String>) request.getAttribute("movieMap");%>
<table>
    <tr>
        <th>Code</th>
        <th>Movie Title</th>
    </tr>
    <%
        if (movieMap != null) {
            for (Map.Entry<String, String> entry : movieMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
    %>
    <tr>
        <td>
            <%= key %>
        </td>
        <td>
            <%= value %>
        </td>
    </tr>
    <% }
    }%>
</table>
</body>
</html>