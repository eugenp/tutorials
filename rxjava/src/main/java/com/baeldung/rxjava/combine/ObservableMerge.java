package com.baeldung.rxjava.combine;

import rx.Observable;

public class ObservableMerge {

    public static void main(String[] args) {
        Observable.merge(
            createObservableOfString("Joe", "Eddie", "Erick"),
            createObservableOfIntegers(1, 2, 3, 4)
        ).subscribe(data -> {
            System.out.println(data);
        });
    }

    static Observable<String> createObservableOfString(String... data) {
        return Observable.from(data);
    }
    
    static Observable<Integer> createObservableOfIntegers(Integer...data) {
        return Observable.from(data);
    }
}
