package com.baeldung.rxjava.filters;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

public class RxJavaTimeFilteringOperatorsIntegrationTest {

    @Test
    public void givenTimedObservable_whenSampling_thenOnlySampleItemsAreEmitted() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> sampledObservable =
                timedObservable.sample(2500L, TimeUnit.MILLISECONDS, testScheduler);

        sampledObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(3, 5, 6);
    }

    @Test
    public void givenTimedObservable_whenThrottlingLast_thenThrottleLastItemsAreEmitted() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.throttleLast(3100L, TimeUnit.MILLISECONDS, testScheduler);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(4, 6);
    }

    @Test
    public void givenRangeObservable_whenThrottlingFirst_thenThrottledFirstItemsAreEmitted() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable =
                timedObservable.throttleFirst(4100L, TimeUnit.MILLISECONDS, testScheduler);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(1, 6);
    }

    @Test
    public void givenTimedObservable_whenThrottlingWithTimeout_thenLastItemIsEmitted() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.throttleWithTimeout(2000L, TimeUnit.MILLISECONDS, testScheduler);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValue(6);
    }

    @Test
    public void givenTimedObservable_whenDebounceOperatorIsApplied_thenLastItemIsEmitted() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.debounce(2000L, TimeUnit.MILLISECONDS, testScheduler);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValue(6);
    }

    @Test
    public void givenTimedObservable_whenUsingTimeout_thenTimeOutException() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.timeout(500L, TimeUnit.MILLISECONDS, testScheduler);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertError(TimeoutException.class);
        subscriber.assertValues(1);
    }

    @Test
    public void givenObservable_whenSkippingUntil_thenItemsAreSkippedUntilSecondObservableEmitsItems() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );


        Observable<Integer> delayedObservable = Observable.just(1)
                .delay(3000, TimeUnit.MILLISECONDS, testScheduler);

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.skipUntil(delayedObservable);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(4, 5, 6);
    }

    @Test
    public void givenObservable_whenSkippingWhile_thenItemsAreEmittedUntilSecondObservableEmitsItems() {

        TestScheduler testScheduler = new TestScheduler();

        Observable<Integer> timedObservable =
                Observable.just(1, 2, 3, 4, 5, 6)
                        .zipWith(
                                Observable.interval(0, 1, TimeUnit.SECONDS, testScheduler),
                                (item, time) -> item
                        );

        TestSubscriber subscriber = new TestSubscriber();

        Observable<Integer> delayedObservable = Observable.just(1)
                .delay(3000, TimeUnit.MILLISECONDS, testScheduler);

        Observable<Integer> filteredObservable = timedObservable.takeUntil(delayedObservable);

        filteredObservable.subscribe(subscriber);

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2, 3);
    }
}
