package com.baeldung.modifyrequest.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.baeldung.modifyrequest.requestwrapper.EscapeHtmlRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
@Profile("filterExample")
public class EscapeHtmlFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(EscapeHtmlFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("Modify the request");

        filterChain.doFilter(new EscapeHtmlRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
