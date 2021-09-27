package com.baeldung.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(MyOncePerRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Inside OncePer Request Filter originated by request {}",request.getRequestURI());
        filterChain.doFilter(request, response);
    }
    protected boolean shouldNotFilterAsyncDispatch() { return true; }


}

