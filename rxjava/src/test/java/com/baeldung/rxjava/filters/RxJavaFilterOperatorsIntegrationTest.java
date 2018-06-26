package com.baeldung.rxjava.filters;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

public class RxJavaFilterOperatorsIntegrationTest {

    @Test
    public void givenRangeObservable_whenFilteringItems_thenOddItemsAreFiltered() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.filter(i -> i % 2 != 0);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        subscriber.assertValues(1, 3, 5, 7, 9);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithTake_thenOnlyFirstThreeItemsAreEmitted() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.take(3);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(3);
        subscriber.assertValues(1, 2, 3);
    }

    @Test
    public void givenObservable_whenFilteringWithTakeWhile_thenItemsEmittedUntilConditionIsVerified() {

        Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 4, 3, 2, 1);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.takeWhile(i -> i < 4);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(3);
        subscriber.assertValues(1, 2, 3);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithTakeFirst_thenOnlyFirstItemIsEmitted() {

        Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 4, 5, 7, 6);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.takeFirst(x -> x > 5);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(7);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithFirst_thenOnlyFirstThreeItemsAreEmitted() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.first();

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(1);
    }

    @Test
    public void givenEmptyObservable_whenFilteringWithFirstOrDefault_thenDefaultValue() {

        Observable<Integer> sourceObservable = Observable.empty();
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.firstOrDefault(-1);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(-1);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithTakeLast_thenLastThreeItemAreEmitted() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.takeLast(3);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(3);
        subscriber.assertValues(8, 9, 10);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithLast_thenOnlyLastItemIsEmitted() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.last(i -> i % 2 != 0);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(9);
    }

    @Test
    public void givenRangeObservable_whenFilteringWithLastAndDefault_thenOnlyDefaultIsEmitted() {

        Observable<Integer> sourceObservable = Observable.range(1, 10);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.lastOrDefault(-1, i -> i > 10);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(-1);
    }

    @Test
    public void givenObservable_whenTakingElementAt_thenItemAtSpecifiedIndexIsEmitted() {

        Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 5, 7, 11);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.elementAt(4);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(7);
    }

    @Test
    public void givenObservable_whenTakingElementAtOrDefault_thenDefaultIsReturned() {

        Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 5, 7, 11);
        TestSubscriber<Integer> subscriber = new TestSubscriber();

        Observable<Integer> filteredObservable = sourceObservable.elementAtOrDefault(7, -1);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(-1);
    }

    @Test
    public void givenMixedTypeObservable_whenFilteringByType_thenOnlyNumbersAreEmitted() {

        Observable sourceObservable = Observable.just(1, "two", 3, "five", 7, 11);
        TestSubscriber subscriber = new TestSubscriber();

        Observable filteredObservable = sourceObservable.ofType(String.class);

        filteredObservable.subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);
        subscriber.assertValues("two", "five");

    }
}
