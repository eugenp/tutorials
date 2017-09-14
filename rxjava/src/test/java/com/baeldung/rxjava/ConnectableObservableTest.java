package com.baeldung.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ConnectableObservableTest {

    @Test
    public void givenConnectableObservable_whenConnect_thenGetMessage() throws InterruptedException {
        final String[] result = {""};
        ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectable.subscribe(i -> result[0] += i);

        assertFalse(result[0].equals("01"));

        connectable.connect();
        Thread.currentThread().sleep(500);

        assertTrue(result[0].equals("01"));
    }
}
