package com.baeldung.micronaut.httpfilters.service;

import io.micronaut.http.HttpRequest;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LogService {

    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);

    public void logHelloMessage(HttpRequest<?> request, String message) {

        LOG.info("{}, Path: {}", message, request.getUri()); 
    }

    public void logOrderFilter(HttpRequest<?> request, String message) {

        LOG.info("{}", message);
    }

    public void logPatternFilter(HttpRequest<?> request, String message) {

        LOG.info("{}", message);
    }
}