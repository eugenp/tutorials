package com.baeldung.micronaut.httpfilters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import com.baeldung.micronaut.httpfilters.service.LogService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class PatternFilterTest {

    final String PATTERN_ARRAY_MESSAGE = "array pattern";
    final String PATTERN_REGEX_MESSAGE = "regex pattern";

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testPatternArray(@Client("/") HttpClient httpClient) {
        var appender = new MemoryAppender();
        Logger l = (Logger) LoggerFactory.getLogger(LogService.class);
        l.addAppender(appender);
        appender.start();

        BlockingHttpClient client = httpClient.toBlocking();
        assertDoesNotThrow(() -> client.retrieve(HttpRequest.GET("/pattern2")));
        assertTrue(appender.getEvents()
                .stream()
                .map(ILoggingEvent::getFormattedMessage)
                .anyMatch(it -> it.contains(PATTERN_ARRAY_MESSAGE)));

        appender.stop();
    }

    @Test
    void testPatternRegex(@Client("/") HttpClient httpClient) {
        var appender = new MemoryAppender();
        Logger l = (Logger) LoggerFactory.getLogger(LogService.class);
        l.addAppender(appender);
        appender.start();

        BlockingHttpClient client = httpClient.toBlocking();
        assertDoesNotThrow(() -> client.retrieve(HttpRequest.GET("/pattern4")));
        assertTrue(appender.getEvents()
                .stream()
                .map(ILoggingEvent::getFormattedMessage)
                .anyMatch(it -> it.contains(PATTERN_REGEX_MESSAGE)));

        appender.stop();
    }

    @Test
    void testPatternGetMethodNotAllowed(@Client("/") HttpClient httpClient) {
        var appender = new MemoryAppender();
        Logger l = (Logger) LoggerFactory.getLogger(LogService.class);
        l.addAppender(appender);
        appender.start();

        BlockingHttpClient client = httpClient.toBlocking();
        assertThrows(HttpClientResponseException.class,
                () -> client.exchange(HttpRequest.GET("/pattern-m")),
                HttpStatus.METHOD_NOT_ALLOWED.getReason());
        appender.stop();
    }
}
