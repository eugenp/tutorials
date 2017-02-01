package com.baelding.rxjava;


import rx.BackpressureOverflow;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class HotObservableOnBackPressure {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1_000_000)
                .onBackpressureBuffer(16, () -> {
                        },
                        BackpressureOverflow.ON_OVERFLOW_DROP_OLDEST)
                .observeOn(Schedulers.computation())
                .subscribe(e -> {
                }, Throwable::printStackTrace);


        Observable.interval(1, TimeUnit.MINUTES)
                .onBackpressureDrop()
//                .onBackpressureLatest()
                .observeOn(Schedulers.io())
                .doOnNext(ComputeFunction::compute)
                .subscribe(v -> {
                }, Throwable::printStackTrace);
        Thread.sleep(10_000);

    }
}
