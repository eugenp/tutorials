package com.baeldung.jetty.httpclient;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveRequest.Event.Type;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RxJava2UnitTest extends AbstractUnitTest {

    @Test
    public void givenReactiveClient_whenRequestedWithBody_ShouldReturnBody() throws Exception {

        Request request = httpClient.newRequest(uri());
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .content(ReactiveRequest.Content.fromString(CONTENT, MediaType.TEXT_PLAIN_VALUE, UTF_8))
            .build();
        Publisher<String> publisher = reactiveRequest.response(ReactiveResponse.Content.asString());

        String responseContent = Single.fromPublisher(publisher)
            .blockingGet();

        Assert.assertEquals(CONTENT, responseContent);
    }

    @Test
    public void givenReactiveClient_whenRequested_ShouldPrintEvents() throws Exception {
        ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, uri())
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

        Assert.assertEquals(6, requestEventTypes.size());
        Assert.assertEquals(5, responseEventTypes.size());

        Assert.assertEquals(actualStatus, HttpStatus.OK_200);
    }

}