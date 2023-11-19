package com.baeldung.resilientapp;

import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({ CallNotPermittedException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleCallNotPermittedException() {
    }

    @ExceptionHandler({ TimeoutException.class })
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public void handleTimeoutException() {
    }

    @ExceptionHandler({ BulkheadFullException.class })
    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public void handleBulkheadFullException() {
    }

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void handleRequestNotPermitted() {
    }
}