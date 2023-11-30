package com.baeldung.modifyrequest.filter;

import com.baeldung.modifyrequest.requestwrapper.EscapeHtmlRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
