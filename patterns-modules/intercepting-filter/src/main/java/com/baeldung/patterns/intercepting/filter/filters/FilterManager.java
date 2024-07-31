package com.baeldung.patterns.intercepting.filter.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
