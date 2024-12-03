package com.baeldung.micronaut.globalexceptionhandler.error.handlers;

import com.baeldung.micronaut.globalexceptionhandler.error.CustomException;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Produces
@Singleton
@Requires(classes = { CustomException.class, ExceptionHandler.class })
public class CustomExceptionHandler implements ExceptionHandler<CustomException, HttpResponse<String>> {

    @Override
    public HttpResponse<String> handle(HttpRequest request, CustomException exception) {
        log.info("handling CustomException: [{}]", exception.getMessage());

        return HttpResponse.ok("Custom Exception was handled");
    }
}
