package com.baeldung.reactive.debugging.consumer;

import com.baeldung.reactive.debugging.consumer.service.FooService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

/**
 * In order to run this live test, start the following classes:
 * - com.baeldung.reactive.debugging.server.ServerDebuggingApplication
 * - com.baeldung.reactive.debugging.consumer.ConsumerDebuggingApplication
 */
public class ConsumerFooServiceLiveTest {

    FooService service = new FooService();

    private static final String BASE_URL = "http://localhost:8082";
    private static final String DEBUG_HOOK_ON = BASE_URL + "/debug-hook-on";
    private static final String DEBUG_HOOK_OFF = BASE_URL + "/debug-hook-off";

    private static WebTestClient client;

    @BeforeAll
    public static void setup() {
        client = WebTestClient.bindToServer()
            .baseUrl(BASE_URL)
            .build();
    }

    @Test
    public void whenRequestingDebugHookOn_thenObtainExpectedMessage() {
        ResponseSpec response = client.get()
            .uri(DEBUG_HOOK_ON)
            .exchange();
        response.expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("DEBUG HOOK ON");
    }

    @Test
    public void whenRequestingDebugHookOff_thenObtainExpectedMessage() {
        ResponseSpec response = client.get()
            .uri(DEBUG_HOOK_OFF)
            .exchange();
        response.expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("DEBUG HOOK OFF");
    }

}
