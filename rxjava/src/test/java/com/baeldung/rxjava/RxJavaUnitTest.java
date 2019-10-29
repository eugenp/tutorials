package com.baeldung.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RxJavaUnitTest {
    @Test
    public void givenObservable_whenZip_shouldAssertBlockingInASameThread() {
        // given
        List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
        List<String> results = new ArrayList<>();
        Observable<String> observable = Observable.from(letters)
          .zipWith(Observable.range(1, Integer.MAX_VALUE), (string, index) -> index + "-" + string);

        // when
        observable.subscribe(results::add);

        // then
        assertThat(results, notNullValue());
        assertThat(results, hasSize(5));
        assertThat(results, hasItems("1-A", "2-B", "3-C", "4-D", "5-E"));
    }

    @Test
    public void givenObservable_whenZip_shouldAssertOnTestSubscriber() {
        // given
        List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
        TestSubscriber<String> subscriber = new TestSubscriber<>();

        Observable<String> observable = Observable.from(letters)
          .zipWith(Observable.range(1, Integer.MAX_VALUE), ((string, index) -> index + "-" + string));

        // when
        observable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        assertThat(subscriber.getOnNextEvents(), hasItems("1-A", "2-B", "3-C", "4-D", "5-E"));
    }

    @Test
    public void givenTestObserver_whenExceptionWasThrowsOnObservable_observerShouldGetError() {
        // given
        List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
        TestSubscriber<String> subscriber = new TestSubscriber<>();

        Observable<String> observable = Observable.from(letters)
          .zipWith(Observable.range(1, Integer.MAX_VALUE), ((string, index) -> index + "-" + string))
          .concatWith(Observable.error(new RuntimeException("error in Observable")));

        // when
        observable.subscribe(subscriber);

        // then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
    }

    @Test
    public void givenObservableThatEmitsEventPerSecond_whenUseAdvanceByTime_shouldEmitEventPerSecond() {
        // given
        List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
        TestScheduler scheduler = new TestScheduler();
        TestSubscriber<String> subscriber = new TestSubscriber<>();
        Observable<Long> tick = Observable.interval(1, TimeUnit.SECONDS, scheduler);

        Observable<String> observable = Observable.from(letters).zipWith(tick, (string, index) -> index + "-" + string);

        observable.subscribeOn(scheduler).subscribe(subscriber);

        // expect
        subscriber.assertNoValues();
        subscriber.assertNotCompleted();

        // when
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        // then
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValues("0-A");

        // when
        scheduler.advanceTimeTo(6, TimeUnit.SECONDS);
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        assertThat(subscriber.getOnNextEvents(), hasItems("0-A", "1-B", "2-C", "3-D", "4-E"));
    }
}
