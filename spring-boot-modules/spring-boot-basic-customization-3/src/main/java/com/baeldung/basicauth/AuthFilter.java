package com.baeldung.basicauth;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String[] credentials = BasicAuthExtractor.extractCredentials(authHeader);

        if (credentials != null) {
            request.setAttribute("rawPassword", credentials[1]);
        }

        filterChain.doFilter(request, response);
    }
}