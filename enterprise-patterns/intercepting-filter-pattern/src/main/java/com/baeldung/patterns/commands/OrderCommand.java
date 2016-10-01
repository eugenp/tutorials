package com.baeldung.enterprise.patterns.front.controller.commands;

import com.baeldung.enterprise.patterns.front.controller.data.Book;
import com.baeldung.enterprise.patterns.front.controller.data.Bookshelf;
import com.baeldung.enterprise.patterns.front.controller.data.Order;
import com.baeldung.enterprise.patterns.front.controller.data.OrderImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        if (request.getMethod().equals("POST")) {
            HttpSession session = request.getSession(false);
            Order order = (Order) session.getAttribute("order");
            if (order == null) {
                String username = (String) session.getAttribute("username");
                order = new OrderImpl(username);
            }
            Bookshelf bookshelf = (Bookshelf) request.getServletContext()
              .getAttribute("bookshelf");
            String isbn = request.getParameter("isbn");
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));
            Book book = bookshelf.get(isbn);
            order.add(book, quantity);
            session.setAttribute("order", order);
            response.sendRedirect(String.format("index?command=Show&isbn=%s", isbn));
        }
    }
}
