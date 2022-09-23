<%@ page import="com.baeldung.patterns.intercepting.filter.data.Book" %>
<%@ page import="java.util.List" %>
<!DOCTYPE>
<html>
    <head>
        <title>Bookshelf: Home</title>
    </head>
    <body>
        <form action="/?command=Search" method="POST">
            <input type="text" id="search" name="q">
            <input type="submit" value="Go!">
        </form>
        <% if (request.getParameter("message") != null) { %>
            <h2><%= request.getParameter("message") %></h2>
        <% } else { %>
            <h2>Welcome to the Bookshelf!</h2>
        <% } %>
        <% for (Book book : (List<Book>) request.getAttribute("books")) { %>
            <hr/>
            <p><b><%= book.getAuthor() %>:</b></p>
            <h3><%= book.getTitle() %></h3>
            <a href="/?command=Show&isbn=<%= book.getIsbn() %>">More...</a>
        <% } %>
        <%@ include file="shopping-cart-hint.jsp"%>
        <%@ include file="visitor-counter.jsp"%>
    </body>
</html>
