package com.baeldung.jetty.httpclient;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.reactive.client.ContentChunk;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveRequest.Event.Type;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Emitter;
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

    @Test
    public void givenReactiveClient_whenRequestedWithFlowableBody_shouldReturn200() {
            Flowable<String> stream = Flowable.fromArray("B", "a", "e", "l", "d", "u", "n", "g");
            ByteBufferPool bufferPool = httpClient.getByteBufferPool();
            Flowable<ContentChunk> chunks = stream.map(item -> item.getBytes())
                .map(bytes -> {
                    ByteBuffer buffer = bufferPool.acquire(bytes.length, true);
                    BufferUtil.append(buffer, bytes, 0, bytes.length);
                    return buffer;
                })
                .map(buffer -> new ContentChunk(buffer, new Callback() {
                    @Override
                    public void succeeded() {
                        complete();
                    }
    
                    @Override
                    public void failed(Throwable x) {
                        complete();
                    }
    
                    @Override
                    public InvocationType getInvocationType() {
                        return InvocationType.NON_BLOCKING;
                    }
    
                    private void complete() {
                        bufferPool.release(buffer);
                    }
                }));
    
            ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, uri())
                .content(ReactiveRequest.Content.fromPublisher(chunks, "text/plain"))
                .build();
    
            String content = Single.fromPublisher(request.response(ReactiveResponse.Content.asString()))
                .blockingGet();
    
            Assert.assertEquals(content, "Baeldung");
    }
    
    @Test
    public void givenReactiveClient_whenRequested_shouldReturnFlowableResponseBody() {
        ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, uri())
            .content(ReactiveRequest.Content.fromString(content, "text/plain", UTF_8))
            .build();
        String responseStr = Flowable.fromPublisher(request.response((response, content) -> content))
                .flatMap(chunk -> Flowable.generate((Emitter<Byte> emitter) -> {
                    ByteBuffer buffer = chunk.buffer;
                    if (buffer.hasRemaining()) {
                        emitter.onNext(buffer.get());
                    } else {
                        chunk.callback.succeeded();
                        emitter.onComplete();
                    }
                }))
                .reduce(new ByteArrayOutputStream(), (accumulator, bytes) -> {
                    accumulator.write(bytes);
                    return accumulator;
                })
                .map(ByteArrayOutputStream::toString)
                .blockingGet();

        Assert.assertEquals(responseStr, content);
    } 
}
