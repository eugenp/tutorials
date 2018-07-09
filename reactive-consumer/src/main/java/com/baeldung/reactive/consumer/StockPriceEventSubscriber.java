package com.baeldung.reactive.consumer;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class StockPriceEventSubscriber extends BaseSubscriber<StockPriceEvent> {

        public void hookOnSubscribe(Subscription subscription) {
                System.out.println("Subscribed");
                request(1);
        }

        public void hookOnNext(StockPriceEvent stockPriceEvent) {
                System.out.println(stockPriceEvent.getStockPrice());
                request(1);
        }
        
        
 }

