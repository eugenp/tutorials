package com.baeldung.ratpack;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingSubscriber<T> implements Subscriber<T> {
    private static final Logger log = LoggerFactory.getLogger(LoggingSubscriber.class);

    private Subscription subscription;
    private long requested;
    private long received;
    private CountDownLatch finished = new CountDownLatch(1);

    @Override
    public void onComplete() {
        log.info("onComplete: sub={}", subscription.hashCode());
        finished.countDown();
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error: sub={}, message={}", subscription.hashCode(), t.getMessage(),t);
        finished.countDown();
    }

    @Override
    public void onNext(T value) {
        log.info("onNext: sub={}, value={}", subscription.hashCode(), value);
        this.received++;
        this.requested++;
        subscription.request(1);
    }

    @Override
    public void onSubscribe(Subscription sub) {
        log.info("onSubscribe: sub={}", sub.hashCode());
        this.subscription = sub;
        this.received = 0;
        this.requested = 1;
        sub.request(1);
    }
    
    
    public long getRequested() {
        return requested;
    }
    
    public long getReceived() {
        return received;
    }

    public void block() {
        try {
            finished.await(10, TimeUnit.SECONDS);
        }
        catch(InterruptedException iex) {
            throw new RuntimeException(iex);
        }
    }

}
