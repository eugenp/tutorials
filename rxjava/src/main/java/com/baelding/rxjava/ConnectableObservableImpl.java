package com.baelding.rxjava;

import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class ConnectableObservableImpl {

    public static void main(String[] args) throws InterruptedException {

        ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectable.subscribe(i -> System.out.println(i));

        System.out.println("Connect");
        connectable.connect();

        Thread.currentThread().sleep(500);
        System.out.println("Sleep");
    }
}
