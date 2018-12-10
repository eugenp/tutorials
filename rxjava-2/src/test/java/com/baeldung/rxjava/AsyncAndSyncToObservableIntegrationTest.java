package com.baeldung.rxjava;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import hu.akarnokd.rxjava2.async.AsyncObservable;
import io.reactivex.Observable;

public class AsyncAndSyncToObservableIntegrationTest {

    AtomicInteger counter = new AtomicInteger();
    Callable<Integer> callable = () -> counter.incrementAndGet();

    /* Method will execute every time it gets subscribed*/
    @Test
    public void givenSyncMethod_whenConvertedWithFromCallable_thenReturnObservable() {

        Observable<Integer> source = Observable.fromCallable(callable);

        for (int i = 1; i < 5; i++) {
            source.test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(i);

            assertEquals(i, counter.get());
        }
    }

    /* Method will execute only once and cache its result.*/
    @Test
    public void givenSyncMethod_whenConvertedWithStart_thenReturnObservable() {

        Observable<Integer> source = AsyncObservable.start(callable);

        for (int i = 1; i < 5; i++) {
            source.test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(1);

            assertEquals(1, counter.get());
        }
    }

    /* Method will execute only once and cache its result.*/
    @Test
    public void givenAsyncMethod_whenConvertedWithFromFuture_thenRetrunObservble() { 

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(callable);
        Observable<Integer> source = Observable.fromFuture(future);

        for (int i = 1; i < 5; i++) {
            source.test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(1);

            assertEquals(1, counter.get());
        }

        executor.shutdown();
    }

    /* Method will execute every time it gets subscribed*/
    @Test
    public void givenAsyncMethod_whenConvertedWithStartFuture_thenRetrunObservble() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Observable<Integer> source = AsyncObservable.startFuture(() -> executor.submit(callable));

        for (int i = 1; i < 5; i++) {
            source.test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(i);

            assertEquals(i, counter.get());
        }

        executor.shutdown();
    }

    /*Method will execute only once and cache its result.*/
    @Test
    public void givenAsyncMethod_whenConvertedWithDeferFuture_thenRetrunObservble() { 
        List<Integer> list = Arrays.asList(new Integer[] { counter.incrementAndGet(), counter.incrementAndGet(), counter.incrementAndGet() });
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Callable<Observable<Integer>> callable = () -> Observable.fromIterable(list);
        Observable<Integer> source = AsyncObservable.deferFuture(() -> exec.submit(callable));
        for (int i = 1; i < 4; i++) {
            source.test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(1, 2, 3);
        }

        exec.shutdown();
    }

}
