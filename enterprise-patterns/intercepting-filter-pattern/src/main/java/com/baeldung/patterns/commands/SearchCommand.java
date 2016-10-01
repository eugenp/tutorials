package com.baeldung.enterprise.patterns.front.controller.commands;

import com.baeldung.enterprise.patterns.front.controller.data.Book;
import com.baeldung.enterprise.patterns.front.controller.data.Bookshelf;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class SearchCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        Bookshelf bookshelf = (Bookshelf) request.getServletContext()
          .getAttribute("bookshelf");
        String q = request.getParameter("q");
        List<Book> books = bookshelf.find(q);
        if (books.size() > 0) {
            request.setAttribute("books", books);
            forward("book-found");
        } else {
            forward("book-notfound");
        }
    }
}
