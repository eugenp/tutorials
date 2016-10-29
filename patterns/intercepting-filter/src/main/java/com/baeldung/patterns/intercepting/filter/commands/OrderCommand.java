package com.baeldung.patterns.intercepting.filter.commands;

import com.baeldung.patterns.intercepting.filter.data.Book;
import com.baeldung.patterns.intercepting.filter.data.Bookshelf;
import com.baeldung.patterns.intercepting.filter.data.Order;
import com.baeldung.patterns.intercepting.filter.data.OrderImpl;

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
            response.sendRedirect(String.format("/?command=Show&isbn=%s", isbn));
        }
    }
}
