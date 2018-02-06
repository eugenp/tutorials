package com.baeldung.rxjava.combine;

import rx.Observable;

public class ObservableZip {

    public static void main(String[] args) {
        Observable<String> obs1 = createObservableOfString("Hello", "Hello");
        Observable<String> obs2 = createObservableOfString("World", "RxJava");
        
        Observable.zip(obs1, obs2, (data1, data2) -> {
            return data1 + " " + data2;
        }).subscribe(zippedData -> {
           System.out.println(zippedData); 
        });
    }

    static Observable<String> createObservableOfString(String...data) {
        return Observable.from(data);
    }
}
