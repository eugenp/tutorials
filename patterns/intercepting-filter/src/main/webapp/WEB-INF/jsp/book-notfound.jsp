<!DOCTYPE html>
<html>
    <head>
        <title>Bookshelf: Title not found</title>
    </head>
    <body>
        <p>Our Bookshelf doesn't contains this title:</p>
        <h2><%= request.getParameter("q") %></h2>
        <%@ include file="visitor-counter.jsp"%>
    </body>
</html>
