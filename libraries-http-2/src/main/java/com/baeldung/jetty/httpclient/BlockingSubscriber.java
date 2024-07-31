package com.baeldung.jetty.httpclient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class BlockingSubscriber implements Subscriber<ReactiveResponse> {
    BlockingQueue<ReactiveResponse> sink = new LinkedBlockingQueue<>(1);

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(ReactiveResponse response) {
        sink.offer(response);
    }

    @Override
    public void onError(Throwable failure) {
    }

    @Override
    public void onComplete() {
    }

    public ReactiveResponse block() throws InterruptedException {
        return sink.poll(5, TimeUnit.SECONDS);
    }
}