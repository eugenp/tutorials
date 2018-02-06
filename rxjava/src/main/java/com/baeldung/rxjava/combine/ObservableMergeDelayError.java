package com.baeldung.rxjava.combine;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import rx.Observable;

public class ObservableMergeDelayError {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public static void main(String[] args) {
        Observable.mergeDelayError(
            createObservableOfStringThatThrows("Hello"),
            createObservableOfStringThatThrows("World"),
            createObservableOfStringThatThrows("RxJava"),
            createObservableOfStringThatThrows(null),
            createObservableOfStringThatThrows("Is Awesome")
        ).subscribe(data -> {
            System.out.println(data);
        }, error -> {
            if (executor != null) {
                executor.shutdown();
            }
        });
    }
    
    static Observable<String> createObservableOfStringThatThrows(String data) {
        Future<String> future = executor.submit(new CallableByRx(data));
        return Observable.from(future);
    }
    
    static class CallableByRx implements Callable<String> {
        private String data;
        
        public CallableByRx(String data) {
            this.data = data;
        }

        @Override
        public String call() throws Exception {
            if (data == null) {
                throw new IllegalArgumentException("Data should not be null");
            }
            return data.toLowerCase();
        }
    }
}
