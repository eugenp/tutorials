package com.baeldung.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Single;

import static junit.framework.Assert.assertTrue;

public class SingleUnitTest {

    @Test
    public void givenSingleObservable_whenSuccess_thenGetMessage() throws InterruptedException {
        String[] result = {""};
        Single<String> single = Observable.just("Hello")
          .toSingle()
          .doOnSuccess(i -> result[0] += i)
          .doOnError(error -> {
              throw new RuntimeException(error.getMessage());
          });
        single.subscribe();
        assertTrue(result[0].equals("Hello"));
    }
}
