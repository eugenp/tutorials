package com.baelding.rxjava;


import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class HotObservableBackPressure {
    public static void main(String[] args) throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source
                .observeOn(Schedulers.computation())
                .subscribe(v -> compute(v), Throwable::printStackTrace);

        for (int i = 0; i < 1_000_000; i++) {
            source.onNext(i);
        }

        Thread.sleep(10_000);
    }

    private static void compute(Integer v) {
        try {
            System.out.println("compute integer v: "+ v);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
