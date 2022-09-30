package com.baeldung.ratpack;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NonCompliantPublisher implements Publisher<Integer> {
    
    private static final Logger log = LoggerFactory.getLogger(NonCompliantPublisher.class);

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        log.info("subscribe");
        subscriber.onSubscribe(new NonCompliantSubscription(subscriber));
    }
    
    private class NonCompliantSubscription implements Subscription {
        private Subscriber<? super Integer> subscriber;
        private int recurseLevel = 0;

        public NonCompliantSubscription(Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            log.info("request: n={}", n);
            if ( recurseLevel > 0 ) {
               return;
            }
            
            recurseLevel++;
            for (int i = 0 ; i < (n + 5) ; i ++ ) {
                subscriber.onNext(i);
            }
            subscriber.onComplete();
        }

        @Override
        public void cancel() {
        }
    }
}