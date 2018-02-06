package com.baeldung.rxjava.combine;

import rx.Observable;

public class ObservableBasic {

    public static void main(String[] args) {
        greeting("Joe", "Eddie");
    }

    public static void greeting(String... names) {
        Observable.from(names).subscribe(name -> System.out.println("Hello " + name));
    }
}
