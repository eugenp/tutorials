package com.baeldung.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public class MultipleSubscribersColdObs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSubscribersColdObs.class);

    public static void main(String[] args) throws InterruptedException {
        defaultBehaviour();
        // subscribeBeforeConnect();

    }

    private static void defaultBehaviour() {
        Observable obs = getObservable();

        LOGGER.info("Subscribing");
        Subscription s1 = obs.subscribe(i -> LOGGER.info("subscriber#1 is printing " + i));
        Subscription s2 = obs.subscribe(i -> LOGGER.info("subscriber#2 is printing " + i));

        s1.unsubscribe();
        s2.unsubscribe();
    }

    private static void subscribeBeforeConnect() throws InterruptedException {
        ConnectableObservable obs = getObservable().publish();

        LOGGER.info("Subscribing");
        obs.subscribe(i -> LOGGER.info("subscriber #1 is printing " + i));
        obs.subscribe(i -> LOGGER.info("subscriber #2 is printing " + i));
        Thread.sleep(1000);
        LOGGER.info("Connecting");
        Subscription s = obs.connect();
        s.unsubscribe();

    }

    private static Observable getObservable() {
        return Observable.create(subscriber -> {
            subscriber.onNext(gettingValue(1));
            subscriber.onNext(gettingValue(2));

            subscriber.add(Subscriptions.create(() -> {
                LOGGER.info("Clear resources");
            }));
        });
    }

    private static Integer gettingValue(int i) {
        LOGGER.info("Getting " + i);
        return i;
    }
}
