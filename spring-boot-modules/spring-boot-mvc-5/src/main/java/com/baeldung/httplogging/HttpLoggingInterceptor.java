package com.baeldung.httplogging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(HttpLoggingInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        log.info("Incoming {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        String requestBody = extractRequestBody(request);
        String responseBody = extractResponseBody(response);

        log.info(
                "HTTP {} {} status={} requestBody={} responseBody={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                requestBody,
                responseBody
        );
    }

    private String extractRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] buf = wrapper.getContentAsByteArray();
            var encoding = request.getCharacterEncoding();
            return getStringValueFromBuffer(buf, encoding);
        }
        return "";
    }

    private String extractResponseBody(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper wrapper) {
            byte[] buf = wrapper.getContentAsByteArray();
            var encoding = response.getCharacterEncoding();
            return getStringValueFromBuffer(buf, encoding);
        }
        return "";
    }

    private String getStringValueFromBuffer(byte[] buffer, String encoding) {
        if (buffer.length > 0) {
            try {
                return new String(buffer, encoding != null ? encoding : StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException ex) {
                return "[unknown-encoding]";
            }
        }
        return "";
    }
}
