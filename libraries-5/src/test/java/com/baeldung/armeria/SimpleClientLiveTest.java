package com.baeldung.armeria;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleClientLiveTest {
    @Test
    void simpleClient() {
        WebClient webClient = WebClient.of();

        AggregatedHttpResponse response = webClient.get("http://localhost:8080/handler").aggregate().join();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("text/plain; charset=utf-8", response.headers().get("content-type"));
        assertEquals("Hello, World!", response.contentUtf8());
    }

    @Test
    void clientWithBaseURL() {
        WebClient webClient = WebClient.of("http://localhost:8080");
        AggregatedHttpResponse response = webClient.get("/handler").aggregate().join();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("text/plain; charset=utf-8", response.headers().get("content-type"));
        assertEquals("Hello, World!", response.contentUtf8());
    }

    @Test
    void post() {
        WebClient webClient = WebClient.of();

        AggregatedHttpResponse response = webClient.post("http://localhost:8080/uppercase-body", "baeldung").aggregate().join();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("text/plain; charset=utf-8", response.headers().get("content-type"));
        assertEquals("Hello: BAELDUNG", response.contentUtf8());
    }

    @Test
    void complexPost() {
        WebClient webClient = WebClient.of("http://localhost:8080");

        HttpRequest request = HttpRequest.of(
            RequestHeaders.builder()
                .method(HttpMethod.POST)
                .path("/uppercase-body")
                .contentType(MediaType.PLAIN_TEXT_UTF_8)
                .build(),
            HttpData.ofUtf8("Baeldung"));
        AggregatedHttpResponse response = webClient.execute(request).aggregate().join();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("text/plain; charset=utf-8", response.headers().get("content-type"));
        assertEquals("Hello: BAELDUNG", response.contentUtf8());
    }

    @Test
    void clientFactory() {
        ClientFactory clientFactory = ClientFactory.builder()
            .connectTimeout(Duration.ofSeconds(10))
            .idleTimeout(Duration.ofSeconds(10))
            .build();
        WebClient webClient = WebClient.builder("http://localhost:8080")
            .factory(clientFactory)
            .build();

        AggregatedHttpResponse response = webClient.get("/handler").aggregate().join();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("text/plain; charset=utf-8", response.headers().get("content-type"));
        assertEquals("Hello, World!", response.contentUtf8());
    }
}
