package com.baeldung.patterns.intercepting.filter.commands;

import com.baeldung.patterns.intercepting.filter.data.Book;
import com.baeldung.patterns.intercepting.filter.data.Bookshelf;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

public class ShowCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        Bookshelf bookshelf = (Bookshelf) request.getServletContext()
          .getAttribute("bookshelf");
        String title = request.getParameter("isbn");
        Book book = bookshelf.get(title);
        request.setAttribute("books", Collections.singletonList(book));
        forward("book-found");
    }
}
