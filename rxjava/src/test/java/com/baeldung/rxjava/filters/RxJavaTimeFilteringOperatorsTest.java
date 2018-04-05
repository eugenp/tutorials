package com.baeldung.rxjava.filters;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Ignore;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

@Ignore("Manual only")
public class RxJavaTimeFilteringOperatorsTest {

    @Test
    public void givenTimedObservable_whenSampling_thenOnlySampleItemsAreEmitted() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> sampledObservable =
          timedObservable.sample(Observable.interval(2500L, TimeUnit.MILLISECONDS));

        sampledObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(3, 5, 6);
    }

    @Test
    public void givenTimedObservable_whenThrottlingLast_thenThrottleLastItemsAreEmitted() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.throttleLast(3100L, TimeUnit.MILLISECONDS);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(4, 6);
    }

    @Test
    public void givenRangeObservable_whenThrottlingFirst_thenThrottledFirstItemsAreEmitted() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable =
          timedObservable.throttleFirst(4100L, TimeUnit.MILLISECONDS);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(1, 6);
    }

    @Test
    public void givenTimedObservable_whenThrottlingWithTimeout_thenLastItemIsEmitted() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.throttleWithTimeout(2000L, TimeUnit.MILLISECONDS);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValue(6);
    }

    @Test
    public void givenTimedObservable_whenDebounceOperatorIsApplied_thenLastItemIsEmitted() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.debounce(2000L, TimeUnit.MILLISECONDS);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValue(6);
    }

    @Test
    public void givenTimedObservable_whenUsingTimeout_thenTimeOutException() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.timeout(500L, TimeUnit.MILLISECONDS);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertError(TimeoutException.class);
        subscriber.assertValues(1);
    }

    @Test
    public void givenObservable_whenSkippingUntil_thenItemsAreSkippedUntilSecondObservableEmitsItems() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );


        Observable<Integer> delayedObservable = Observable.just(1)
                                                          .delay(3000, TimeUnit.MILLISECONDS);

        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = timedObservable.skipUntil(delayedObservable);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(4, 5, 6);
    }

    @Test
    public void givenObservable_whenSkippingWhile_thenItemsAreEmittedUntilSecondObservableEmitsItems() throws InterruptedException {

        Observable<Integer> timedObservable =
          Observable.just(1, 2, 3, 4, 5, 6)
                    .zipWith(
                      Observable.interval(0, 1, TimeUnit.SECONDS),
                      (item, time) -> item
                    );

        TestSubscriber subscriber = new TestSubscriber();

        Observable<Integer> delayedObservable = Observable.just(1)
                                                          .delay(3000, TimeUnit.MILLISECONDS);

        Observable<Integer> filteredObservable = timedObservable.takeUntil(delayedObservable);

        filteredObservable.subscribe(subscriber);

        Thread.sleep(7000);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2, 3);
    }
}
