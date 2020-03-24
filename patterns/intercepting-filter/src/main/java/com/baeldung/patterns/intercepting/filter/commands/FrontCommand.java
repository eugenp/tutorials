package com.baeldung.patterns.intercepting.filter.commands;

import com.baeldung.patterns.intercepting.filter.filters.FilterManager;
import com.baeldung.patterns.intercepting.filter.filters.OnIntercept;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand implements OnIntercept {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private boolean intercept;

    public FrontCommand() {
    }

    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void process() throws ServletException, IOException {
        FilterManager.process(request, response, this);
    }

    public void forward(String target) throws ServletException, IOException {
        if (intercept) {
            return;
        }
        String path = String.format("/WEB-INF/jsp/%s.jsp", target);
        RequestDispatcher dispatcher = request.getServletContext()
          .getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    public void intercept() {
        intercept = true;
    }
}
