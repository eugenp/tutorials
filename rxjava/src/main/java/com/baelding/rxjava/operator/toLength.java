package com.baelding.rxjava.operator;

import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

public class toLength implements Transformer<String, Integer> {
    public toLength() {
        super();
    }

    @Override
    public Observable<Integer> call(Observable<String> source) {

        return source.map(new Func1<String, Integer>() {
            @Override
            public Integer call(String str) {
                return str.length();
            }
        });
    }
}