package com.baeldung.spring.multipleheaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class MultipleHeadersUnitTest {

    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void givenRequestWithHeaders_whenSendingRequest_thenAssertHeadersAreSent() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value()));

        WebClient client = WebClient.builder()
            .baseUrl(mockWebServer.url("/").toString())
            .build();

        ResponseEntity<Void> response = client.get()
            .headers(headers -> {
                headers.put("X-Request-Id", Collections.singletonList(RANDOM_UUID));
                headers.put("Custom-Header", Collections.singletonList("CustomValue"));
            })
            .retrieve()
            .toBodilessEntity()
            .block();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals(RANDOM_UUID, recordedRequest.getHeader("X-Request-Id"));
        assertEquals("CustomValue", recordedRequest.getHeader("Custom-Header"));
    }

    @Test
    public void givenRequestWithDefaultHeaders_whenSendingRequest_thenAssertHeadersAreSent() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value()));

        WebClient client = WebClient.builder()
            .baseUrl(mockWebServer.url("/").toString())
            .defaultHeaders(headers -> {
                headers.put("X-Request-Id", Collections.singletonList(RANDOM_UUID));
                headers.put("Custom-Header", Collections.singletonList("CustomValue"));
            })
            .build();

        ResponseEntity<Void> response = client.get()
            .retrieve()
            .toBodilessEntity()
            .block();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals(RANDOM_UUID, recordedRequest.getHeader("X-Request-Id"));
        assertEquals("CustomValue", recordedRequest.getHeader("Custom-Header"));
    }

    @Test
    public void givenRequestWithDynamicHeaders_whenSendingRequest_thenAssertHeadersAreSent() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value()));

        ExchangeFilterFunction dynamicHeadersFilter = (request, next) -> next.exchange(ClientRequest.from(request)
            .headers(headers -> {
                headers.put("X-Request-Id", Collections.singletonList(RANDOM_UUID));
                headers.put("Custom-Header", Collections.singletonList("CustomValue"));
            })
            .build());

        WebClient client = WebClient.builder()
            .baseUrl(mockWebServer.url("/").toString())
            .filter(dynamicHeadersFilter)
            .build();

        ResponseEntity<Void> response = client.get()
            .retrieve()
            .toBodilessEntity()
            .block();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals(RANDOM_UUID, recordedRequest.getHeader("X-Request-Id"));
        assertEquals("CustomValue", recordedRequest.getHeader("Custom-Header"));
    }

}
