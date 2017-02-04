package com.baelding.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ColdObservableBackpressure {
    public static void main(String[] args) throws InterruptedException {

        Observable.range(1, 1_000_000)
                .observeOn(Schedulers.computation())
                .subscribe(ComputeFunction::compute);

        Thread.sleep(10_000);
    }

}
