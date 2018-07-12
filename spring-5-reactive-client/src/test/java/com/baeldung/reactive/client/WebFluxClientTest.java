package com.baeldung.reactive.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WebFluxClientTest {

    private MockWebServer server;
    private WebClient webClient;

    @Before
    public void setup() {
        this.server = new MockWebServer();
        this.webClient = WebClient.create(this.server.url("/").toString());
    }

    @After
    public void shutdown() throws IOException {
        this.server.shutdown();
    }

    @Test
    public void whenQueryingServer_thenCorrectOutput() {
        this.server.enqueue(new MockResponse().setBody("anyString"));

        Flux<String> bookNames = new WebFluxClient().getBookNames(webClient);

        assertEquals(true, bookNames.blockFirst().equals("anyString"));
    }
}