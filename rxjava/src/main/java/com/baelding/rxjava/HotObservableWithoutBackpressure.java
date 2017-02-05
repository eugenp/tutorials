package com.baelding.rxjava;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class HotObservableWithoutBackpressure {
    public static void main(String[] args) throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.<Integer> create();

        source
          .observeOn(Schedulers.computation())
          .subscribe(ComputeFunction::compute, Throwable::printStackTrace);

        for (int i = 0; i < 1_000_000; i++) {
            source.onNext(i);
        }
        Thread.sleep(10_000);
    }
}
