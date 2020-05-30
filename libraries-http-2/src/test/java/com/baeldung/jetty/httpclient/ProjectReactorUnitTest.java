package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Mono;

public class ProjectReactorUnitTest extends AbstractUnitTest {

    @Test
    public void givenReactiveClient_whenRequested_shouldReturn200() throws Exception {

        Request request = httpClient.newRequest(uri());
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        ReactiveResponse response = Mono.from(publisher)
            .block();

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);

    }
}
