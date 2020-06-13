package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;

public class ReactiveStreamsUnitTest extends AbstractUnitTest {

    @Test
    public void givenReactiveClient_whenRequested_shouldReturn200() throws Exception {

        Request request = httpClient.newRequest(uri());
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        BlockingSubscriber subscriber = new BlockingSubscriber();
        publisher.subscribe(subscriber);
        ReactiveResponse response = subscriber.block();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

}
