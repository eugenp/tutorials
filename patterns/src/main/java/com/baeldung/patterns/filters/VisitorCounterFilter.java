package com.baeldung.patterns.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class VisitorCounterFilter extends BaseFilter {
    private static Set<String> users = new HashSet<>();

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        String username = (String) session.getAttribute("username");
        if (!users.contains(username)) {
            users.add(username);
        }
        request.setAttribute("counter", users.size());
        chain.doFilter(request, response);
    }
}
