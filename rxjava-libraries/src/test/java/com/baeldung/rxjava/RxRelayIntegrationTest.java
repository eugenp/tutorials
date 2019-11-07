package com.baeldung.rxjava;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.ReplayRelay;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RxRelayIntegrationTest {

    @Test
    public void whenObserverSubscribedToPublishRelay_thenItReceivesEmittedEvents () {
        PublishRelay<Integer> publishRelay = PublishRelay.create();
        TestObserver<Integer> firstObserver = TestObserver.create();
        TestObserver<Integer> secondObserver = TestObserver.create();
        publishRelay.subscribe(firstObserver);
        firstObserver.assertSubscribed();
        publishRelay.accept(5);
        publishRelay.accept(10);
        publishRelay.subscribe(secondObserver);
        secondObserver.assertSubscribed();
        publishRelay.accept(15);
        //First Observer will receive all events
        firstObserver.assertValues(5, 10, 15);
        //Second Observer will receive only last event
        secondObserver.assertValue(15);
    }

    @Test
    public void whenObserverSubscribedToBehaviorRelayWithoutDefaultValue_thenItIsEmpty() {
        BehaviorRelay<Integer> behaviorRelay = BehaviorRelay.create();
        TestObserver<Integer> firstObserver = new TestObserver<>();
        behaviorRelay.subscribe(firstObserver);
        firstObserver.assertEmpty();
    }

    @Test
    public void whenObserverSubscribedToBehaviorRelay_thenItReceivesDefaultValue() {
        BehaviorRelay<Integer> behaviorRelay = BehaviorRelay.createDefault(1);
        TestObserver<Integer> firstObserver = new TestObserver<>();
        behaviorRelay.subscribe(firstObserver);
        firstObserver.assertValue(1);
    }

    @Test
    public void whenObserverSubscribedToBehaviorRelay_thenItReceivesEmittedEvents () {
        BehaviorRelay<Integer> behaviorRelay = BehaviorRelay.create();
        TestObserver<Integer> firstObserver = TestObserver.create();
        TestObserver<Integer> secondObserver = TestObserver.create();
        behaviorRelay.accept(5);
        behaviorRelay.subscribe(firstObserver);
        behaviorRelay.accept(10);
        behaviorRelay.subscribe(secondObserver);
        behaviorRelay.accept(15);
        firstObserver.assertValues(5, 10, 15);
        secondObserver.assertValues(10, 15);
    }
    @Test
    public void whenObserverSubscribedToReplayRelay_thenItReceivesEmittedEvents () {
        ReplayRelay<Integer> replayRelay = ReplayRelay.create();
        TestObserver<Integer> firstObserver = TestObserver.create();
        TestObserver<Integer> secondObserver = TestObserver.create();
        replayRelay.subscribe(firstObserver);
        replayRelay.accept(5);
        replayRelay.accept(10);
        replayRelay.accept(15);
        replayRelay.subscribe(secondObserver);
        firstObserver.assertValues(5, 10, 15);
        secondObserver.assertValues(5, 10, 15);

    }

    @Test
    public void whenObserverSubscribedToReplayRelayWithLimitedSize_thenItReceivesEmittedEvents () {
        ReplayRelay<Integer> replayRelay = ReplayRelay.createWithSize(2);
        TestObserver<Integer> firstObserver = TestObserver.create();
        replayRelay.accept(5);
        replayRelay.accept(10);
        replayRelay.accept(15);
        replayRelay.accept(20);
        replayRelay.subscribe(firstObserver);

        firstObserver.assertValues(15, 20);

    }


    @Test
    public void whenObserverSubscribedToReplayRelayWithMaxAge_thenItReceivesEmittedEvents () throws InterruptedException {
        ReplayRelay<Integer> replayRelay = ReplayRelay.createWithTime(2000, TimeUnit.MILLISECONDS, new SingleScheduler());
        TestObserver<Integer> firstObserver = TestObserver.create();
        replayRelay.accept(5);
        replayRelay.accept(10);
        replayRelay.accept(15);
        replayRelay.accept(20);
        Thread.sleep(3000);
        replayRelay.subscribe(firstObserver);
        firstObserver.assertEmpty();
    }

    @Test
    public void whenTwoObserversSubscribedToRandomRelay_thenOnlyOneReceivesEvent () {
        RandomRelay randomRelay = new RandomRelay();
        TestObserver<Integer> firstObserver = TestObserver.create();
        TestObserver<Integer> secondObserver = TestObserver.create();
        randomRelay.subscribe(firstObserver);
        randomRelay.subscribe(secondObserver);
        randomRelay.accept(5);
        if(firstObserver.values().isEmpty()) {
            secondObserver.assertValue(5);
        } else {
            firstObserver.assertValue(5);
            secondObserver.assertEmpty();
        }
    }
}

