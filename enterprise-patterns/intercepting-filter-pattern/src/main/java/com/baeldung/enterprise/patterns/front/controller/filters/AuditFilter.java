package com.baeldung.enterprise.patterns.front.controller.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuditFilter extends BaseFilter {
    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            request.setAttribute("username", session.getAttribute("username"));
        }
        chain.doFilter(request, response);
    }
}
