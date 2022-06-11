package com.baeldung.apikeysecretauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeySecretFilter extends OncePerRequestFilter {
    public static final String SCHEME = "api-key ";
    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public ApiKeySecretFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull FilterChain chain) throws ServletException, IOException {
        final String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith(SCHEME)) {
            chain.doFilter(req, res);
            return;
        }

        //Authorization: api-key <API_KEY>:<API:SECRET>
        final String[] credentials = header.substring(SCHEME.length())
            .split(":");
        if (credentials.length != 2) {
            res.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        final String apiKey = credentials[0];
        final String apiSecret = credentials[1];
        final ApiKeySecretAuthenticationToken token = new ApiKeySecretAuthenticationToken(apiKey, apiSecret);

        try {
            Authentication authResult = this.authenticationManager.authenticate(token);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResult);
            SecurityContextHolder.setContext(context);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(req, res, e);
            return;
        }

        chain.doFilter(req, res);
    }
}
