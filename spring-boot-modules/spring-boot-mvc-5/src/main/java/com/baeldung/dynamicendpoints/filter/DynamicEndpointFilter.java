package com.baeldung.dynamicendpoints.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class DynamicEndpointFilter extends OncePerRequestFilter {

    private Environment environment;

    public DynamicEndpointFilter(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String regex = this.environment.getProperty("endpoint.regex");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        boolean matches = matcher.matches();

        if (!matches) {
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service is unavailable");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
