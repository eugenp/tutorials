package com.baeldung.concurrent.futurevscompletablefuturevsrxjava;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class FutureVsCompletableFutureVsRxJavaUnitTest {

    @Test
    public void whenRetrievingObjectWithBasicFuture_thenExpectOnlySingleDataPointSet() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<TestObject> future = exec.submit(new ObjectCallable());
        TestObject retrievedObject = future.get();
        assertEquals(10, retrievedObject.getDataPointOne());
        assertEquals(0, retrievedObject.getDataPointTwo());
    }

    @Test
    public void givenACompletableFuture_whenHydratingObjectAfterRetrieval_thenExpectBothDataPointsSet() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        ObjectHydrator objectHydrator = new ObjectHydrator();
        CompletableFuture<TestObject> future = CompletableFuture.supplyAsync(new ObjectSupplier(), exec)
          .thenApply(objectHydrator::hydrateTestObject);
        TestObject retrievedObject = future.get();
        assertEquals(10, retrievedObject.getDataPointOne());
        assertEquals(20, retrievedObject.getDataPointTwo());
    }

    @Test
    public void givenAnObservable_whenRequestingData_thenItIsRetrieved() {
        ObjectHydrator objectHydrator = new ObjectHydrator();
        Observable<TestObject> observable = Observable.fromCallable(new ObjectCallable()).map(objectHydrator::hydrateTestObject);
        observable.subscribe(System.out::println);
    }

    @Test
    public void givenAnObservable_whenPushedData_thenItIsReceived() {
        PublishSubject<Integer> source = PublishSubject.create();
        Observable<Integer> observable = source.observeOn(Schedulers.computation());
        observable.subscribe(System.out::println, (throwable) -> System.out.println("Error"), () -> System.out.println("Done"));

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onComplete();
    }

}
