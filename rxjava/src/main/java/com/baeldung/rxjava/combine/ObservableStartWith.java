package com.baeldung.rxjava.combine;

import rx.Observable;

public class ObservableStartWith {

    public static void main(String[] args) {
        createObservableOfStrings("Justin", "Edward")
            .startWith("Hello")
            .subscribe(data -> System.out.println(data));
    }

    static Observable<String> createObservableOfStrings(String...data) {
        return Observable.from(data);
    }
}
