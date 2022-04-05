package com.baeldung.rxjava;

import rx.Observable;
import rx.Single;

public class SingleImpl {

    public static void main(String[] args) {

        Single<String> single = Observable.just("Hello")
          .toSingle()
          .doOnSuccess(System.out::print)
          .doOnError(e -> {
              throw new RuntimeException(e.getMessage());
          });
        single.subscribe();
    }
}
