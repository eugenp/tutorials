package com.baelding.rxjava;

import rx.BackpressureOverflow;
import rx.Observable;
import rx.schedulers.Schedulers;

public class HotObservableOnBackpressure {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1_000_000).onBackpressureBuffer(16, () -> {
        }, BackpressureOverflow.ON_OVERFLOW_DROP_OLDEST).observeOn(Schedulers.computation()).subscribe(e -> {
        }, Throwable::printStackTrace);

        Observable.range(1, 1_000_000).onBackpressureDrop().observeOn(Schedulers.io()).doOnNext(ComputeFunction::compute).subscribe(v -> {
        }, Throwable::printStackTrace);
        Thread.sleep(10_000);

    }
}
