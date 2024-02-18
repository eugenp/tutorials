package com.baeldung.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
public class LoggingFilterAutowiringSupport implements Filter {

    @Autowired
    LoggingService loggingService;

    @Override
    public void init(FilterConfig filterConfig) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
            filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        loggingService.log(httpServletRequest.getMethod(),httpServletRequest.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
