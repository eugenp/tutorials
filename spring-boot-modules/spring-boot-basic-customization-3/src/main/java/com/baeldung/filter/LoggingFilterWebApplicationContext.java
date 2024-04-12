package com.baeldung.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class LoggingFilterWebApplicationContext implements Filter {

    private LoggingService loggingService;

    @Override
    public void init(FilterConfig filterConfig) {
        loggingService = WebApplicationContextUtils
            .getRequiredWebApplicationContext(filterConfig.getServletContext())
            .getBean(LoggingService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        loggingService.log(httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
