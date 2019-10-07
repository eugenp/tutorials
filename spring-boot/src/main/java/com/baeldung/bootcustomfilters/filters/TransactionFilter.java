package com.baeldung.bootcustomfilters.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A filter to create transaction before and commit it once request completes
 * The current implemenatation is just for demo
 * @author hemant
 *
 */
@Component
@Order(1)
public class TransactionFilter implements Filter {

	private final static Logger LOG = LoggerFactory.getLogger(TransactionFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		LOG.info("Initializing filter :{}", this);
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		LOG.info("Starting Transaction for req :{}", req.getRequestURI());
		chain.doFilter(request, response);
		LOG.info("Committing Transaction for req :{}", req.getRequestURI());
	}

	@Override
	public void destroy() {
		LOG.warn("Destructing filter :{}", this);
	}
}
