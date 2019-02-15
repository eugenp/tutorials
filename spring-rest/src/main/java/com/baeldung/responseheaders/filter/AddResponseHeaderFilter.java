package com.baeldung.responseheaders.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
