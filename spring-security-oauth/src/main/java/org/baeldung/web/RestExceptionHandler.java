package org.baeldung.web;

import java.io.Serializable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler implements Serializable {

    private static final long serialVersionUID = -3861125729653781371L;

    public RestExceptionHandler() {
        super();
    }

    // API

    // 500
    @ExceptionHandler({ UserApprovalRequiredException.class, UserRedirectRequiredException.class })
    public ResponseEntity<Object> handleRedirect(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getLocalizedMessage());
        throw ex;
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.info(request.getHeader("x-ratelimit-remaining"));
        logger.error("500 Status Code", ex);
        final String response = "Error Occurred : " + ex.getMessage();
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}