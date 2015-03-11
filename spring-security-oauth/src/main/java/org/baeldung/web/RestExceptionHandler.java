package org.baeldung.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler implements Serializable {

    private static final long serialVersionUID = -3861125729653781371L;

    public RestExceptionHandler() {
        super();
    }

    // API

    // 4xx

    @ExceptionHandler({ OAuth2AccessDeniedException.class })
    public ModelAndView handleOAuth2AccessDeniedException(final OAuth2AccessDeniedException ex, final WebRequest request) {
        logger.error("403 Status Code", ex);
        final String response = "Error Occurred - Forbidden: " + ex.getMessage();
        final ModelAndView model = new ModelAndView("submissionResponse");
        model.addObject("msg", response);
        return model;
        // return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    public ModelAndView handleHttpClientErrorException(final HttpClientErrorException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final String response = "Error Occurred - To Many Requests: " + ex.getMessage();
        final ModelAndView model = new ModelAndView("submissionResponse");
        model.addObject("msg", response);
        return model;
        // return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS, request);
    }

    // HttpClientErrorException
    // 500

    @ExceptionHandler({ UserApprovalRequiredException.class, UserRedirectRequiredException.class })
    public ResponseEntity<Object> handleRedirect(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getLocalizedMessage());
        throw ex;
    }

    @ExceptionHandler({ Exception.class })
    public ModelAndView handleInternal(final RuntimeException ex, final WebRequest request, final HttpServletResponse response) {
        logger.info(response.getHeader("x-ratelimit-remaining"));
        logger.error("500 Status Code", ex);
        final String message = "Error Occurred: " + ex.getLocalizedMessage();
        final ModelAndView model = new ModelAndView("submissionResponse");
        model.addObject("msg", message);
        return model;
    }
}