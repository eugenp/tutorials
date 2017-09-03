package com.baelding.rxjava;

import rx.Observable;
import rx.Single;

public class SingleImpl {

    public static void main(String[] args) {

        Single<String> single = Observable.just("Hello").toSingle()
                .doOnSuccess(
                        (i) -> {
                            System.out.println(i);
                        })
                .doOnError(
                        (error) -> {
                            throw new RuntimeException(error.getMessage());
                        });
        single.subscribe();


    }

}
