package com.baeldung.reactive.consumer;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.SignalType;

public class ExampleSubscriber<T> extends BaseSubscriber<T> {

        public void hookOnSubscribe(Subscription subscription) {
                System.out.println("Subscribed");
                request(1);
        }

        public void hookOnNext(T value) {
                System.out.println(value);
                request(1);
        }
        
        public void hookFinally(SignalType signalType) {
            if(signalType==SignalType.ON_COMPLETE) {
                System.out.println("Completed");
            }
    }
        
        
}

