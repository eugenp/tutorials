package com.baeldung.rxjava.combine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.observers.TestSubscriber;

public class ObservableMergeVsConcatUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(ObservableMergeVsConcatUnitTest.class);

    @Test
    public void givenTwoSynchronousObservables_whenConcatenated_thenAllValuesEmittedInOrder() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        Observable<Integer> observable3 = Observable.just(7, 8, 9);

        TestSubscriber<Integer> testSubscriberForConcat = new TestSubscriber<>();

        Observable.concat(observable1, observable2, observable3)
            .subscribe(testSubscriberForConcat);

        testSubscriberForConcat.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void givenTwoSynchronousObservables_whenMerged_thenAllValuesEmitted() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        Observable<Integer> observable3 = Observable.just(7, 8, 9);

        TestSubscriber<Integer> testSubscriberForMerge = new TestSubscriber<>();

        Observable.merge(observable1, observable2, observable3)
            .subscribe(testSubscriberForMerge);

        testSubscriberForMerge.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void givenTwoAsynchronousObservables_whenConcatenated_thenAllValuesEmittedInOrder() {
        Observable<Integer> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 1)
            .take(3);

        Observable<Integer> observable2 = Observable.interval(30, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 4)
            .take(7);

        TestSubscriber<Integer> testSubscriberForConcat = new TestSubscriber<>();

        Observable.concat(observable1, observable2)
            .subscribe(testSubscriberForConcat);

        testSubscriberForConcat.awaitTerminalEvent();

        testSubscriberForConcat.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    public void givenTwoAsynchronousObservables_whenMerged_thenAllValuesEmittedInterleaved() {
        Observable<Integer> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 1)
            .take(3);
        Observable<Integer> observable2 = Observable.interval(30, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 4)
            .take(7);
        TestSubscriber<Integer> testSubscriberForMerge = new TestSubscriber<>();

        Observable.merge(observable1, observable2)
            .subscribe(testSubscriberForMerge);

        testSubscriberForMerge.awaitTerminalEvent();

        testSubscriberForMerge.assertValues(4, 5, 6, 1, 7, 8, 9, 2, 10, 3);
    }

    @Test
    public void givenTwoAsynchronousObservablesWithSimilarDelays_whenConcat_thenAllValuesEmittedInOrder() {
        Observable<Integer> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 1)
            .take(3);

        Observable<Integer> observable2 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 4)
            .take(3);

        TestSubscriber<Integer> testSubscriberForConcat = new TestSubscriber<>();

        Observable.concat(observable1, observable2)
            .subscribe(testSubscriberForConcat);

        testSubscriberForConcat.awaitTerminalEvent();

        testSubscriberForConcat.assertValues(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void givenTwoAsynchronousObservablesWithSimilarDelays_whenMerged_thenAllValuesEmittedRegardlessOfOrder() {
        Observable<Integer> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 1)
            .take(3);

        Observable<Integer> observable2 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i -> i.intValue() + 4)
            .take(3);

        TestSubscriber<Integer> testSubscriberForMerge = new TestSubscriber<>();

        Observable.merge(observable1, observable2)
            .subscribe(testSubscriberForMerge);

        testSubscriberForMerge.awaitTerminalEvent();

        List<Integer> actual = testSubscriberForMerge.getOnNextEvents();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
        logger.info("actual emissions: {}", actual);
    }
}
