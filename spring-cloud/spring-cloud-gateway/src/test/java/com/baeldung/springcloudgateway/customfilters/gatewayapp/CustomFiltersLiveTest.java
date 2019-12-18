package com.baeldung.springcloudgateway.customfilters.gatewayapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.baeldung.springcloudgateway.customfilters.gatewayapp.utils.LoggerListAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * This test requires:
 * * the service in com.baeldung.service running
 * * the 'second service' in com.baeldung.secondservice running
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomFiltersLiveTest {

    @LocalServerPort
    String port;

    private WebTestClient client;

    @BeforeEach
    public void clearLogList() {
        LoggerListAppender.clearEventList();
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    public void whenCallServiceThroughGateway_thenAllConfiguredFiltersGetExecuted() {
        ResponseSpec response = client.get()
            .uri("/service/resource")
            .exchange();

        response.expectStatus()
            .isOk()
            .expectHeader()
            .doesNotExist("Bael-Custom-Language-Header")
            .expectBody(String.class)
            .isEqualTo("Service Resource");

        assertThat(LoggerListAppender.getEvents())
            // Global Pre Filter
            .haveAtLeastOne(eventContains("Global Pre Filter executed"))
            // Global Post Filter
            .haveAtLeastOne(eventContains("Global Post Filter executed"))
            // Global Pre and Post Filter
            .haveAtLeastOne(eventContains("First Pre Global Filter"))
            .haveAtLeastOne(eventContains("Last Post Global Filter"))
            // Logging Filter Factory
            .haveAtLeastOne(eventContains("Pre GatewayFilter logging: My Custom Message"))
            .haveAtLeastOne(eventContains("Post GatewayFilter logging: My Custom Message"))
            // Modify Request
            .haveAtLeastOne(eventContains("Modify request output - Request contains Accept-Language header:"))
            .haveAtLeastOne(eventContainsExcept("Removed all query params: ", "locale"))
            // Modify Response
            .areNot(eventContains("Added custom header to Response"))
            // Chain Request
            .haveAtLeastOne(eventContains("Chain Request output - Request contains Accept-Language header:"));
    }

    @Test
    public void givenRequestWithLocaleQueryParam_whenCallServiceThroughGateway_thenAllConfiguredFiltersGetExecuted() {
        ResponseSpec response = client.get()
            .uri("/service/resource?locale=en")
            .exchange();

        response.expectStatus()
            .isOk()
            .expectHeader()
            .exists("Bael-Custom-Language-Header")
            .expectBody(String.class)
            .isEqualTo("Service Resource");

        assertThat(LoggerListAppender.getEvents())
            // Modify Response
            .haveAtLeastOne(eventContains("Added custom header to Response"))
            .haveAtLeastOne(eventContainsExcept("Removed all query params: ", "locale"));
    }

    /**
     * This condition will be successful if the event contains a substring
     */
    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }

    /**
     * This condition will be successful if the event contains a substring, but not another one
     */
    private Condition<ILoggingEvent> eventContainsExcept(String substring, String except) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring)
            && !entry.getFormattedMessage()
                .contains(except))),
            String.format("entry with message '%s'", substring));
    }
}
