package com.baeldung.filters;

import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CacheRequestContentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            if (contentType == null || !contentType.contains("multipart/form-data")) {
                request = new ContentCachingRequestWrapper((HttpServletRequest) request);
            }
        }
        chain.doFilter(request, response);
    }
}
