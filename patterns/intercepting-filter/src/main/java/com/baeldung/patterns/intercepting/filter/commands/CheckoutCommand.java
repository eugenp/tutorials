package com.baeldung.patterns.intercepting.filter.commands;

import com.baeldung.patterns.intercepting.filter.data.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckoutCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        HttpSession session = request.getSession(false);
        if (request.getMethod().equals("POST")) {
            session.removeAttribute("order");
            response.sendRedirect("/?command=Home&message=Thank you for buying!");
        } else {
            Order order = (Order) session.getAttribute("order");
            Double total = order.getItems().entrySet().stream()
              .map(entry -> entry.getKey().getPrice() * entry.getValue())
              .reduce((p1, p2) -> p1 + p2)
              .orElse(0.00);
            request.setAttribute("total", total);
            forward("shopping-cart");
        }
    }
}
