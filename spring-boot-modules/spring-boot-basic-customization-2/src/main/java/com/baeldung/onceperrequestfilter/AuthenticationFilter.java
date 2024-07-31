package com.baeldung.onceperrequestfilter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws
            ServletException, IOException {
        String usrName = request.getHeader("userName");
        logger.info("Successfully authenticated user  " +
                usrName);
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}