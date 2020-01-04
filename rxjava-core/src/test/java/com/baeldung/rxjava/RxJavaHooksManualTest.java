package com.baeldung.rxjava;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxJavaHooksManualTest {

    private boolean initHookCalled = false;
    private boolean hookCalled = false;
    
    @Test
    public void givenIOScheduler_whenCalled_shouldExecuteTheHooks() {

        RxJavaPlugins.setInitIoSchedulerHandler((scheduler) -> {
            initHookCalled = true;
            return scheduler.call();
        });

        RxJavaPlugins.setIoSchedulerHandler((scheduler) -> {
            hookCalled = true;
            return scheduler;
        });

        Observable.range(1, 10)
            .map(v -> v * 2)
            .subscribeOn(Schedulers.io())
            .test();
        assertTrue(hookCalled && initHookCalled);
    }

    @Test
    public void givenNewThreadScheduler_whenCalled_shouldExecuteTheHook() {

        RxJavaPlugins.setInitNewThreadSchedulerHandler((scheduler) -> {
            initHookCalled = true;
            return scheduler.call();
        });

        RxJavaPlugins.setNewThreadSchedulerHandler((scheduler) -> {
            hookCalled = true;
            return scheduler;
        });

        Observable.range(1, 15)
            .map(v -> v * 2)
            .subscribeOn(Schedulers.newThread())
            .test();
        assertTrue(hookCalled && initHookCalled);
    }

    @Test
    public void givenSingleScheduler_whenCalled_shouldExecuteTheHooks() {

        RxJavaPlugins.setInitSingleSchedulerHandler((scheduler) -> {
            initHookCalled = true;
            return scheduler.call();
        });

        RxJavaPlugins.setSingleSchedulerHandler((scheduler) -> {
            hookCalled = true;
            return scheduler;
        });

        Observable.range(1, 10)
            .map(v -> v * 2)
            .subscribeOn(Schedulers.single())
            .test();
        assertTrue(hookCalled && initHookCalled);

    }
    
    @After
    public void reset() {
        hookCalled = false;
        initHookCalled = false;
        RxJavaPlugins.reset();
    }
}
