package com.baeldung.chaindofilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Order(1)
@Component
public class FirstFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger(FirstFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        LOG.info("Processing the First Filter");
        // Omit chain.doFilter() on purpose
    }
}
