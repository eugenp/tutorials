package com.baelding.rxjava.operator;

import rx.Observable.Operator;
import rx.Subscriber;

public class cleanString implements Operator<String, String> {

    public cleanString() {
        super();
    }

    @Override
    public Subscriber<? super String> call(final Subscriber<? super String> subscriber) {
        return new Subscriber<String>(subscriber) {
            @Override
            public void onCompleted() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
            }

            @Override
            public void onNext(String item) {
                if (!subscriber.isUnsubscribed()) {
                    final String result = item.replaceAll("[^A-Za-z0-9]", "");
                    subscriber.onNext(result);
                }
            }
        };
    }

}