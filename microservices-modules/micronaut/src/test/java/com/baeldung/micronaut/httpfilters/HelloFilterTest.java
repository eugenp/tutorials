package com.baeldung.micronaut.httpfilters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import com.baeldung.micronaut.httpfilters.service.LogService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class HelloFilterTest {

    final String MESSAGE = "Hello message";

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testHelloMessageFilter(@Client("/") HttpClient httpClient) {
        var appender = new MemoryAppender();
        Logger l = (Logger) LoggerFactory.getLogger(LogService.class);
        l.addAppender(appender);
        appender.start();

        BlockingHttpClient client = httpClient.toBlocking();
        assertDoesNotThrow(() -> client.retrieve(HttpRequest.GET("/hello")));
        assertTrue(appender.getEvents()
                .stream()
                .map(ILoggingEvent::getFormattedMessage)
                .anyMatch(it -> it.contains(MESSAGE)));

        appender.stop();
    }
}
