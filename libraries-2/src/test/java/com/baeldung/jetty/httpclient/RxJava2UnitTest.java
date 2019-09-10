package com.baeldung.jetty.httpclient;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveRequest.Event.Type;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RxJava2UnitTest extends AbstractTest {

    @Test
    public void givenReactiveClient_whenRequestedWithBody_ShouldReturnBody() throws Exception {
        
        Request request = httpClient.newRequest(uri());
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .content(ReactiveRequest.Content.fromString(content, "text/plain", UTF_8))
            .build();
        Publisher<String> publisher = reactiveRequest.response(ReactiveResponse.Content.asString());

        String responseContent = Single.fromPublisher(publisher)
            .blockingGet();

        Assert.assertEquals(responseContent, content);
    }

    @Test
    public void givenReactiveClient_whenRequested_ShouldPrintEvents() throws Exception {
        ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, uri())
            .content(ReactiveRequest.Content.fromString(content, "text/plain", UTF_8))
            .build();
        Publisher<ReactiveRequest.Event> requestEvents = request.requestEvents();
        Publisher<ReactiveResponse.Event> responseEvents = request.responseEvents();
        CountDownLatch latch = new CountDownLatch(2);
        Flowable.fromPublisher(requestEvents)
            .map(ReactiveRequest.Event::getType)
            .subscribe(new Subscriber<Type>() {
                private Subscription subscription;

                @Override
                public void onSubscribe(Subscription subscription) {
                    System.out.println("----- Request Events ------");
                    this.subscription = subscription;
                    subscription.request(1);
                }

                @Override
                public void onNext(Type type) {
                    System.out.println(type.name());
                    subscription.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                }

                @Override
                public void onComplete() {
                    System.out.println("----- Response Events ------");
                    latch.countDown();
                }
            });

        Flowable.fromPublisher(responseEvents)
            .map(ReactiveResponse.Event::getType)
            .subscribe(new Subscriber<org.eclipse.jetty.reactive.client.ReactiveResponse.Event.Type>() {
                private Subscription subscription;

                @Override
                public void onSubscribe(Subscription subscription) {
                    this.subscription = subscription;
                    subscription.request(1);
                }

                @Override
                public void onNext(org.eclipse.jetty.reactive.client.ReactiveResponse.Event.Type type) {
                    System.out.println(type.name());
                    subscription.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                }

                @Override
                public void onComplete() {
                    latch.countDown();
                }
            });

        ReactiveResponse response = Single.fromPublisher(request.response())
            .blockingGet();

        Assert.assertTrue(latch.await(5, TimeUnit.SECONDS));
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

}
