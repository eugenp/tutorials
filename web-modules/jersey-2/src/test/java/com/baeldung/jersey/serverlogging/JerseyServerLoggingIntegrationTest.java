package com.baeldung.jersey.serverlogging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

class JerseyServerLoggingIntegrationTest {

    private static HttpServer server;
    private static final URI BASE_URI = URI.create("http://localhost:8080/api");

    @BeforeAll
    static void setup() {
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new JerseyServerLoggingApp());
    }

    @AfterAll
    static void teardown() {
        server.shutdownNow();
    }

    @Test
    void whenRequestMadeWithLoggingFilter_thenCustomLogsAreWritten() {
        Logger logger = (Logger) LoggerFactory.getLogger(CustomServerLoggingFilter.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        listAppender.list.clear();

        Response response = ClientBuilder.newClient()
            .target(BASE_URI + "/logging")
            .request()
            .get();
        assertEquals(200, response.getStatus());

        boolean requestLogFound = listAppender.list.stream()
            .anyMatch(event -> event.getFormattedMessage().contains("Incoming request: GET http://localhost:8080/api/logging"));
        boolean responseLogFound = listAppender.list.stream()
            .anyMatch(event -> event.getFormattedMessage().contains("Outgoing response: GET http://localhost:8080/api/logging - Status 200"));

        assertEquals(true, requestLogFound, "Request log not found");
        assertEquals(true, responseLogFound, "Response log not found");

        logger.detachAppender(listAppender);
    }
}
