package com.baeldung.jetty.httpclient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ReactiveStreamsUnitTest extends AbstractTest {
    
    @Test
    public void givenReactiveClient_whenRequested_shouldReturn200() throws Exception {
       
        Request request = httpClient.newRequest(uri());
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request).build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        CountDownLatch latch = new CountDownLatch(1);
        publisher.subscribe(new Subscriber<ReactiveResponse>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(ReactiveResponse response) {
                Assert.assertNotNull(response);
                Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
            }

            @Override
            public void onError(Throwable failure) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        Assert.assertTrue(latch.await(5, TimeUnit.SECONDS));
    }
}
