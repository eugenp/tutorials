package com.baeldung.enterprise.patterns.front.controller.commands;

import com.baeldung.enterprise.patterns.front.controller.data.Book;
import com.baeldung.enterprise.patterns.front.controller.data.BookshelfImpl;

import javax.servlet.ServletException;
import java.io.IOException;

public class SearchCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        Book book = new BookshelfImpl().getInstance()
          .findByTitle(request.getParameter("title"));
        if (book != null) {
            request.setAttribute("book", book);
            forward("book-found");
        } else {
            forward("book-notfound");
        }
    }
}
