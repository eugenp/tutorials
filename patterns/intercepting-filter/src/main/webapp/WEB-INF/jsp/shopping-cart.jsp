<%@ page import="com.baeldung.patterns.intercepting.filter.data.Book" %>
<%@ page import="com.baeldung.patterns.intercepting.filter.data.Order" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Bookshelf: Checkout</title>
    </head>
    <body>
        <p>You are about to buy the following books:</p>
        <h2>Shopping Cart</h2>
        <% Order order = (Order) session.getAttribute("order"); %>
        <ul>
            <% for (Map.Entry<Book, Integer> entry : order.getItems().entrySet()) { %>
                <li>
                    <b><%= entry.getValue() %> x <%= entry.getKey().getPrice() %></b>
                    <h3><%= entry.getKey().getTitle() %></h3>
                    <i> by <%= entry.getKey().getAuthor()%></i>
                </li>
            <% } %>
        </ul>
        <p>
            <b>Total: <%= request.getAttribute("total") %></b>
        </p>
        <form action="/?command=Checkout" method="POST">
            <input type="submit" value="Donate :)">
        </form>
    </body>
</html>
