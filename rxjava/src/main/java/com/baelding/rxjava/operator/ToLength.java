package com.baelding.rxjava.operator;

import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

public class ToLength implements Transformer<String, Integer> {
    public ToLength() {
        super();
    }

    @Override
    public Observable<Integer> call(Observable<String> source) {
        return source.map(String::length);
    }
}