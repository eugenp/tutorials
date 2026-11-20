package com.baeldung.httplogging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class CachingHttpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequest =
                new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper cachingResponse =
                new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(cachingRequest, cachingResponse);
        } finally {
            cachingResponse.copyBodyToResponse();
        }
    }
}
