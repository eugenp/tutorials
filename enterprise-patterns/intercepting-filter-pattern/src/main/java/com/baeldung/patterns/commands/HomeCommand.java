package com.baeldung.enterprise.patterns.front.controller.commands;

import com.baeldung.enterprise.patterns.front.controller.data.Bookshelf;

import javax.servlet.ServletException;
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
