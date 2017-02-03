package com.baelding.rxjava;


import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ColdObservableBackPressure {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1_000_000)
                .observeOn(Schedulers.computation())
                .subscribe(v -> ComputeFunction.compute(v), Throwable::printStackTrace);

        Thread.sleep(10_000);


//        Observable.range(1, 1_000_000) //implementation of reactive pull backpressure on cold observable
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onStart() {
//                        request(1);
//                    }
//
//                    public void onNext(Integer v) {
//                        compute(v);
//
//                        request(1);
//                    }
//
//                    @Override
//                    public void onError(Throwable ex) {
//                        ex.printStackTrace();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("Done!");
//                    }
//                });

    }


}
