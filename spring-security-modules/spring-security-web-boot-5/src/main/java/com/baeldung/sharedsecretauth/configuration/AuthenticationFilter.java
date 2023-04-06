package com.baeldung.sharedsecretauth.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Value("${api.auth.header.name}")
    private String apiAuthHeaderName;

    @Value("${api.auth.secret}")
    private String apiAuthHeaderSecret;

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("Custom Auth Filter called for URI {}", httpServletRequest.getRequestURI());

        try {

            if (httpServletRequest.getHeader(apiAuthHeaderName) != null &&
                    httpServletRequest.getHeader(apiAuthHeaderName).equals(apiAuthHeaderSecret)) {

                PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(apiAuthHeaderName,
                        httpServletRequest.getHeader(apiAuthHeaderName), new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(preAuthenticatedAuthenticationToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch(AuthenticationException authenticationException) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        LOGGER.info("Custom Auth Filter completed for URI {}", httpServletRequest.getRequestURI());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/app/health".equals(path);
    }
}