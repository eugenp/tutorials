package com.baeldung.responseheaders.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/filter-response-header/*")
public class AddResponseHeaderFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddResponseHeaderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // add special initialization requirements here
        LOGGER.trace("Initializing filter...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Baeldung-Example-Filter-Header", "Value-Filter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // clean up any resource being held by the filter here
        LOGGER.trace("Destroying filter...");
    }

}
