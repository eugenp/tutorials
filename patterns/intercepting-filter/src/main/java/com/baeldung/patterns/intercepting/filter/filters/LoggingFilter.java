package com.baeldung.patterns.intercepting.filter.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@WebFilter(servletNames = "intercepting-filter")
public class LoggingFilter extends BaseFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String username = Optional
          .ofNullable(httpServletRequest.getAttribute("username"))
          .map(Object::toString)
          .orElse("guest");
        log.info("Request from '{}@{}': {}?{}", username, request.getRemoteAddr(),
          httpServletRequest.getRequestURI(), request.getParameterMap());
    }
}
