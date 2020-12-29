package com.baeldung.exclude_urls_filter.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class HeaderValidatorFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    String path = request.getRequestURI();
	    if ("/health".equals(path)) {
	    	filterChain.doFilter(request, response);
	    	return;
	    }
	    String countryCode = request.getHeader("X-Country-Code");
	    if (!"US".equals(countryCode)) {
	        response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid Locale");
	        return;
	    }
	
	    filterChain.doFilter(request, response);
	}
}