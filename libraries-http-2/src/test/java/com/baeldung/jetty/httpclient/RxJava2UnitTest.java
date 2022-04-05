package com.baeldung.jetty.httpclient;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveRequest.Event.Type;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RxJava2UnitTest extends AbstractUnitTest {
    
    protected static int port = 9082;
    
    @BeforeAll
    public static void init() {
        startServer(new RequestHandler(), port);
        startClient();
    }

    @Test
    public void givenReactiveClient_whenRequestedWithBody_ShouldReturnBody() throws Exception {

        Request request = httpClient.newRequest(uri(port));
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .content(ReactiveRequest.Content.fromString(CONTENT, MediaType.TEXT_PLAIN_VALUE, UTF_8))
            .build();
        Publisher<String> publisher = reactiveRequest.response(ReactiveResponse.Content.asString());

        String responseContent = Single.fromPublisher(publisher)
            .blockingGet();

        assertEquals(CONTENT, responseContent);
    }

    @Test
    public void givenReactiveClient_whenRequested_ShouldPrintEvents() throws Exception {
        ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, uri(port))
            .content(ReactiveRequest.Content.fromString(CONTENT, MediaType.TEXT_PLAIN_VALUE, UTF_8))
            .build();
        Publisher<ReactiveRequest.Event> requestEvents = request.requestEvents();
        Publisher<ReactiveResponse.Event> responseEvents = request.responseEvents();

        List<Type> requestEventTypes = new ArrayList<>();
        List<ReactiveResponse.Event.Type> responseEventTypes = new ArrayList<>();

        Flowable.fromPublisher(requestEvents)
            .map(ReactiveRequest.Event::getType)
            .subscribe(requestEventTypes::add);

        Flowable.fromPublisher(responseEvents)
            .map(ReactiveResponse.Event::getType)
            .subscribe(responseEventTypes::add);

        Single<ReactiveResponse> response = Single.fromPublisher(request.response());
        int actualStatus = response.blockingGet()
            .getStatus();

        assertEquals(6, requestEventTypes.size());
        assertEquals(5, responseEventTypes.size());

        assertEquals(actualStatus, HttpStatus.OK_200);
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