package com.baeldung.rxjava;

import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxJavaHooksUnitTest {

    @Test
    public void givenCompletable_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnCompletableAssembly(completable -> {
                System.out.println("Assembling Completable");
                return completable;
            });
            Completable.fromSingle(Single.just(1));
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenCompletable_whenSubscribed_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnCompletableSubscribe((completable, observer) -> {
                System.out.println("Subscribing to Completable");
                return observer;
            });

            Completable.fromSingle(Single.just(1))
                .test(true);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenObservable_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnObservableAssembly(observable -> {
                System.out.println("Assembling Observable");
                return observable;
            });

            Observable.range(1, 10);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenObservable_whenSubscribed_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnObservableSubscribe((observable, observer) -> {
                System.out.println("Suscribing to Observable");
                return observer;
            });

            Observable.range(1, 10)
                .test(true);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenConnectableObservable_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnConnectableObservableAssembly(connectableObservable -> {
                System.out.println("Assembling ConnectableObservable");
                return connectableObservable;
            });

            ConnectableObservable.range(1, 10)
                .publish()
                .connect();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenFlowable_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnFlowableAssembly(flowable -> {
                System.out.println("Assembling Flowable");
                return flowable;
            });

            Flowable.range(1, 10);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenFlowable_whenSubscribed_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnFlowableSubscribe((flowable, observer) -> {
                System.out.println("Suscribing to Flowable");
                return observer;
            });

            Flowable.range(1, 10)
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenConnectableFlowable_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnConnectableFlowableAssembly(connectableFlowable -> {
                System.out.println("Assembling ConnectableFlowable");
                return connectableFlowable;
            });

            ConnectableFlowable.range(1, 10)
                .publish()
                .connect();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenParallel_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnParallelAssembly(parallelFlowable -> {
                System.out.println("Assembling ParallelFlowable");
                return parallelFlowable;
            });

            Flowable.range(1, 10)
                .parallel();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenMaybe_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnMaybeAssembly(maybe -> {
                System.out.println("Assembling Maybe");
                return maybe;
            });

            Maybe.just(1);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenMaybe_whenSubscribed_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnMaybeSubscribe((maybe, observer) -> {
                System.out.println("Suscribing to Maybe");
                return observer;
            });

            Maybe.just(1)
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenSingle_whenAssembled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setOnSingleAssembly(single -> {
                System.out.println("Assembling Single");
                return single;
            });

            Single.just(1);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenSingle_whenSubscribed_shouldExecuteTheHook() {

        try {
            RxJavaPlugins.setOnSingleSubscribe((single, observer) -> {
                System.out.println("Suscribing to Single");
                return observer;
            });

            Single.just(1)
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenAnyScheduler_whenCalled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setScheduleHandler((runnable) -> {
                System.out.println("Executing Scheduler");
                return runnable;
            });

            Observable.range(1, 10)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.single())
                .test();

            Observable.range(1, 10)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.computation())
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenComputationScheduler_whenCalled_shouldExecuteTheHooks() {
        try {
            RxJavaPlugins.setInitComputationSchedulerHandler((scheduler) -> {
                System.out.println("Initializing Computation Scheduler");
                return scheduler.call();
            });
            RxJavaPlugins.setComputationSchedulerHandler((scheduler) -> {
                System.out.println("Executing Computation Scheduler");
                return scheduler;
            });

            Observable.range(1, 10)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.computation())
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenIOScheduler_whenCalled_shouldExecuteTheHooks() {
        try {
            RxJavaPlugins.setInitIoSchedulerHandler((scheduler) -> {
                System.out.println("Initializing IO Scheduler");
                return scheduler.call();
            });

            RxJavaPlugins.setIoSchedulerHandler((scheduler) -> {
                System.out.println("Executing IO Scheduler");
                return scheduler;
            });

            Observable.range(1, 10)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.io())
                .test();
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenNewThreadScheduler_whenCalled_shouldExecuteTheHook() {
        try {
            RxJavaPlugins.setInitNewThreadSchedulerHandler((scheduler) -> {
                System.out.println("Initializing newThread Scheduler");
                return scheduler.call();
            });

            RxJavaPlugins.setNewThreadSchedulerHandler((scheduler) -> {
                System.out.println("Executing newThread Scheduler");
                return scheduler;
            });

            Observable.range(1, 15)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.newThread())
                .test();

        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenSingleScheduler_whenCalled_shouldExecuteTheHooks() {
        try {
            RxJavaPlugins.setInitSingleSchedulerHandler((scheduler) -> {
                System.out.println("Initializing Single Scheduler");
                return scheduler.call();
            });

            RxJavaPlugins.setSingleSchedulerHandler((scheduler) -> {
                System.out.println("Executing Single Scheduler");
                return scheduler;
            });

            Observable.range(1, 10)
                .map(v -> v * 2)
                .subscribeOn(Schedulers.single())
                .test();

        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void givenObservable_whenError_shouldExecuteTheHook() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            System.out.println("Handling error" + throwable.getCause());
        });

        Observable.error(new IllegalStateException())
            .subscribe();
    }
}
