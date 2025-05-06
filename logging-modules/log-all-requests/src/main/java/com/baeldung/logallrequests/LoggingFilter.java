package com.baeldung.logallrequests;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            logRequest(httpRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);

            chain.doFilter(request, responseWrapper);

            logResponse(httpRequest, responseWrapper);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void logRequest(HttpServletRequest request) {
        logger.info("Incoming Request: [{}] {}", request.getMethod(), request.getRequestURI());
        request.getHeaderNames().asIterator().forEachRemaining(header ->
          logger.info("Header: {} = {}", header, request.getHeader(header))
        );
    }

    private void logResponse(HttpServletRequest request, ResponseWrapper responseWrapper) throws IOException {
        logger.info("Outgoing Response for [{}] {}: Status = {}",
          request.getMethod(), request.getRequestURI(), responseWrapper.getStatus());
        logger.info("Response Body: {}", responseWrapper.getBodyAsString());
    }
}