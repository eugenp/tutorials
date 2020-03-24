<%@ page import="com.baeldung.patterns.intercepting.filter.data.Book" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Bookshelf: Title found</title>
    </head>
    <body>
        <p>Our Bookshelf contains following titles:</p>
        <% for (Book book : (List<Book>) request.getAttribute("books")) { %>
            <h2><%= book.getTitle() %></h2>
            <p>Author: <%= book.getAuthor() %></p>
            <form action="/?command=Order" method="POST">
                <label for="quantity">Quantity:</label>
                <input type="text" id="quantity" name="quantity" value="1" required>
                <input type="hidden" name="isbn" value="<%= book.getIsbn() %>">
                <input type="submit" value="Buy it: <%= book.getPrice() %>$">
                <a href="/?command=Home">Go back...</a>
            </form>
        <% } %>
        <%@ include file="shopping-cart-hint.jsp"%>
        <%@ include file="visitor-counter.jsp"%>
    </body>
</html>
