package com.baeldung.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlowableIntegrationTest {

    @Test public void whenFlowableIsCreated_thenItIsProperlyInitialized() {
        Flowable<Integer> integerFlowable = Flowable.just(1, 2, 3, 4);
        assertNotNull(integerFlowable);
    }

    @Test public void whenFlowableIsCreatedFromObservable_thenItIsProperlyInitialized() throws InterruptedException {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3);
        Flowable<Integer> integerFlowable = integerObservable.toFlowable(BackpressureStrategy.BUFFER);
        assertNotNull(integerFlowable);

    }

    @Test public void whenFlowableIsCreatedFromFlowableOnSubscribe_thenItIsProperlyInitialized() throws InterruptedException {
        FlowableOnSubscribe<Integer> flowableOnSubscribe = flowableEmitter -> flowableEmitter.onNext(1);
        Flowable<Integer> integerFlowable = Flowable.create(flowableOnSubscribe, BackpressureStrategy.BUFFER);
        assertNotNull(integerFlowable);
    }

    @Test public void thenAllValuesAreBufferedAndReceived() {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());
        Observable observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable.toFlowable(BackpressureStrategy.BUFFER).observeOn(Schedulers.computation()).test();

        testSubscriber.awaitTerminalEvent();

        List<Integer> receivedInts = testSubscriber.getEvents().get(0).stream().mapToInt(object -> (int) object).boxed().collect(Collectors.toList());

        assertEquals(testList, receivedInts);
    }

    @Test public void whenDropStrategyUsed_thenOnBackpressureDropped() {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());

        Observable observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable.toFlowable(BackpressureStrategy.DROP).observeOn(Schedulers.computation()).test();
        testSubscriber.awaitTerminalEvent();
        List<Integer> receivedInts = testSubscriber.getEvents().get(0).stream().mapToInt(object -> (int) object).boxed().collect(Collectors.toList());

        assertThat(receivedInts.size() < testList.size());
        assertThat(!receivedInts.contains(100000));
    }

    @Test public void whenMissingStrategyUsed_thenException() {
        Observable observable = Observable.range(1, 100000);
        TestSubscriber subscriber = observable.toFlowable(BackpressureStrategy.MISSING).observeOn(Schedulers.computation()).test();

        subscriber.awaitTerminalEvent();
        subscriber.assertError(MissingBackpressureException.class);
    }

    @Test public void  whenErrorStrategyUsed_thenExceptionIsThrown() {
            Observable observable = Observable.range(1, 100000);
        TestSubscriber subscriber = observable.toFlowable(BackpressureStrategy.ERROR).observeOn(Schedulers.computation()).test();

        subscriber.awaitTerminalEvent();
        subscriber.assertError(MissingBackpressureException.class);
    }

    @Test public void whenLatestStrategyUsed_thenTheLastElementReceived() {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());
        Observable observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).test();

        testSubscriber.awaitTerminalEvent();
        List<Integer> receivedInts = testSubscriber.getEvents().get(0).stream().mapToInt(object -> (int) object).boxed().collect(Collectors.toList());

        assertThat(receivedInts.size() < testList.size());
        assertThat(receivedInts.contains(100000));
    }

}