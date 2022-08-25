package com.baeldung.ratpack;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Non-thread safe !!!
class CompliantPublisher implements Publisher<Integer> {
    String name;

    private static final Logger log = LoggerFactory.getLogger(CompliantPublisher.class); 
    private long available;


    public CompliantPublisher(long available) {
        this.available = available;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        log.info("subscribe");
        subscriber.onSubscribe(new CompliantSubscription(subscriber));
        
    }
    
    
    private class CompliantSubscription implements Subscription {

        private Subscriber<? super Integer> subscriber;
        private int recurseLevel;
        private long requested;
        private boolean cancelled;

        public CompliantSubscription(Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            log.info("request: requested={}, available={}", n, available);
            requested += n;
            if ( recurseLevel > 0 ) {
               return;
            }
            
            recurseLevel++;
            for (int i = 0 ; i < (requested) && !cancelled && available > 0 ; i++, available-- ) {
                subscriber.onNext(i);
            }
            subscriber.onComplete();
        }

        @Override
        public void cancel() {
            cancelled  = true;
        }
        
    }
    
}