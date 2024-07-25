package com.baeldung.patterns.intercepting.filter.commands;

import com.baeldung.patterns.intercepting.filter.data.Bookshelf;

import jakarta.servlet.ServletException;
import java.io.IOException;

public class HomeCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        Bookshelf bookshelf = (Bookshelf) request.getServletContext()
          .getAttribute("bookshelf");
        request.setAttribute("books", bookshelf);
        forward("home");
    }
}
