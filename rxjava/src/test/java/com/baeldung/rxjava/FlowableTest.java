package com.baeldung.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlowableTest {

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
        FlowableOnSubscribe<Integer> flowableOnSubscribe = flowableEmitter -> {
            flowableEmitter.onNext(1);
        };
        Flowable<Integer> integerFlowable = Flowable.create(flowableOnSubscribe, BackpressureStrategy.BUFFER);
        assertNotNull(integerFlowable);
    }

    @Test public void givenFlowableWithBufferStrategy_whenSourceEmitsFasterThanConsumerConsumes_thenAllValuesAreBufferedAndReceived() throws InterruptedException {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());
        List listToFill = new ArrayList();

        Observable observable = Observable.fromIterable(testList);
        observable.toFlowable(BackpressureStrategy.BUFFER).observeOn(Schedulers.computation()).subscribe(listToFill::add);
        Thread.sleep(5000);
        assertEquals(testList, listToFill);
    }

    @Test public void givenFlowableWithDropStrategy_whenSourceEmitsFasterThanConsumerConsumes_thenNotAllValuesAreReceived() throws InterruptedException {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());
        List listToFill = new ArrayList();

        Observable observable = Observable.fromIterable(testList);
        observable.toFlowable(BackpressureStrategy.DROP).observeOn(Schedulers.computation()).subscribe(listToFill::add);
        Thread.sleep(5000);
        assertThat(listToFill.size() < testList.size());
        assertThat(!listToFill.contains(100000));
    }

    @Test
    public void givenFlowableWithMissingStrategy_whenSourceEmitsFasterThanConsumerConsumes_thenExceptionIsThrown() throws InterruptedException {
        Observable observable = Observable.range(1, 100000);
        TestSubscriber subscriber =observable.toFlowable(BackpressureStrategy.MISSING).observeOn(Schedulers.computation()).test();
        subscriber.awaitTerminalEvent();
        subscriber.assertError(MissingBackpressureException.class);
    }

    @Test
    public void givenFlowableWithErrorStrategy_whenSourceEmitsFasterThanConsumerConsumes_thenExceptionIsThrown() throws InterruptedException {
        Observable observable = Observable.range(1, 100000);
        TestSubscriber subscriber =observable.toFlowable(BackpressureStrategy.ERROR).observeOn(Schedulers.computation()).test();

        subscriber.awaitTerminalEvent();
        subscriber.assertError(MissingBackpressureException.class);
    }

    @Test
    public void givenFlowableWithLatestStrategy_whenSourceEmitsFasterThanConsumerConsumes_thenExceptionIsThrown() throws InterruptedException {
        List testList = IntStream.range(0, 100000).boxed().collect(Collectors.toList());
        List listToFill = new ArrayList();

        Observable observable = Observable.fromIterable(testList);
        observable.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).subscribe(listToFill::add);
        Thread.sleep(6000);
        assertThat(listToFill.size() < testList.size());
        assertThat(listToFill.contains(100000));
    }

}