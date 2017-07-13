<%@ page import="com.baeldung.patterns.intercepting.filter.data.Order" %>
<% if (session != null && session.getAttribute("order") != null) { %>
    <% Order order = ((Order) session.getAttribute("order")); %>
    <% if (order != null && order.getItems().size() > 0) { %>
        <hr/>
        <p>
            Your shopping cart is holding
            <% if (order.getItems().size() == 1) { %>
                1 item.
            <% } else { %>
                <%= (order.getItems().size()) %> items.
            <% } %>
            <a href="/?command=Checkout">Checkout</a>
        </p>
    <% } %>
<% } %>
