package com.baeldung.cats.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SimpleAuthenticationFilter extends OncePerRequestFilter {

    private static final String TEST_TOKEN = "valid-token";
    private static final String BEARER = "Bearer ";
    private static final List<String> AUTHORIZE = List.of("swagger", "docs");

    private final Logger log = LoggerFactory.getLogger(SimpleAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (AUTHORIZE.stream()
            .noneMatch(uri::contains)) {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
                accessDenied(response, uri);
                return;
            }

            String token = authorizationHeader.substring(BEARER.length());
            if (!valid(token)) {
                accessDenied(response, uri);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void accessDenied(HttpServletResponse response, String uri) throws IOException {
        log.warn("access denied to {}", uri);
        response.setStatus(401);
        response.getWriter()
            .write("access denied");
    }

    private boolean valid(String token) {
        return TEST_TOKEN.equals(token);
    }
}
