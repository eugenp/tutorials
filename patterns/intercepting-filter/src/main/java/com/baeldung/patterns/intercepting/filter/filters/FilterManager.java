package com.baeldung.patterns.intercepting.filter.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterManager {
    public static void process(
      HttpServletRequest request,
      HttpServletResponse response,
      OnIntercept callback
    ) throws ServletException, IOException {
        FilterChain filterChain = new FilterChainImpl(
          new AuthenticationFilter(callback),
          new VisitorCounterFilter()
        );
        filterChain.doFilter(request, response);
    }
}
