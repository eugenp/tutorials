package com.baeldung.modifyrequest.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baeldung.modifyrequest.requestwrapper.EscapeHtmlRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EscapeHtmlRequestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(EscapeHtmlRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        EscapeHtmlRequestWrapper escapeHtmlRequestWrapper = new EscapeHtmlRequestWrapper(request);
        return true;
    }
}
