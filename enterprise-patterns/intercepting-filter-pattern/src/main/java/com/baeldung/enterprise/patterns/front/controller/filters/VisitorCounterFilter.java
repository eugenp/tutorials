package com.baeldung.enterprise.patterns.front.controller.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(servletNames = "front-controller")
public class VisitorCounterFilter extends BaseFilter {
    private int counter;

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        request.setAttribute("counter", ++counter);
        chain.doFilter(request, response);
    }
}
