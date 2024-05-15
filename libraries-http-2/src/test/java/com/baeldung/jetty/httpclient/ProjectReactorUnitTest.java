package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Mono;

public class ProjectReactorUnitTest extends AbstractUnitTest {

    protected static int port = 9080;
    
    @BeforeAll
    public static void init() {
        startServer(new RequestHandler(), port);
        startClient();
    }
    
    @Test
    public void givenReactiveClient_whenRequested_shouldReturn200() throws Exception {

        Request request = httpClient.newRequest(uri(port));
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        ReactiveResponse response = Mono.from(publisher)
            .block();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.OK_200);

    }
    
    @AfterAll
    public static void dispose() throws Exception {
        if (httpClient != null) {
            httpClient.stop();
        }
        if (server != null) {
            server.stop();
        }
    }
    
}
