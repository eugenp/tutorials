package com.baeldung.cachedrequest;


import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Order
@Component
@WebFilter(filterName = "printRequestContentFilter", urlPatterns = "/*")
public class PrintRequestContentFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("IN  PrintRequestContentFilter ");
        InputStream inputStream = httpServletRequest.getInputStream();
        byte[] body = StreamUtils.copyToByteArray(inputStream);
        System.out.println("In PrintRequestContentFilter. Request body is: " + new String(body));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
