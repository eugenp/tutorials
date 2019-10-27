package com.baeldung.rxjava.operators;

import org.junit.Test;
import rx.Observable;
import rx.observables.MathObservable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RxMathematicalOperatorsUnitTest {

    @Test
    public void givenRangeNumericObservable_whenCalculatingAverage_ThenSuccessfull() {
        // given
        Observable<Integer> sourceObservable = Observable.range(1, 20);

        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        MathObservable.averageInteger(sourceObservable)
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(10);
    }

    @Test
    public void givenRangeNumericObservable_whenCalculatingSum_ThenSuccessfull() {
        // given
        Observable<Integer> sourceObservable = Observable.range(1, 20);
        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        MathObservable.sumInteger(sourceObservable)
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(210);

    }

    @Test
    public void givenRangeNumericObservable_whenCalculatingMax_ThenSuccessfullObtainingMaxValue() {
        // given
        Observable<Integer> sourceObservable = Observable.range(1, 20);
        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        MathObservable.max(sourceObservable)
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(20);
    }

    @Test
    public void givenRangeNumericObservable_whenCalculatingMin_ThenSuccessfullObtainingMinValue() {
        // given
        Observable<Integer> sourceObservable = Observable.range(1, 20);
        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        MathObservable.min(sourceObservable)
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(1);
    }

    @Test
    public void givenItemObservable_whenCalculatingMaxWithComparator_ThenSuccessfullObtainingMaxItem() {
        // given
        Item five = new Item(5);
        List<Item> list = Arrays.asList(new Item(1), new Item(2), new Item(3), new Item(4), five);
        Observable<Item> itemObservable = Observable.from(list);

        TestSubscriber<Item> subscriber = TestSubscriber.create();

        // when
        MathObservable.from(itemObservable)
            .max(Comparator.comparing(Item::getId))
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(five);

    }

    @Test
    public void givenItemObservable_whenCalculatingMinWithComparator_ThenSuccessfullObtainingMinItem() {
        // given
        Item one = new Item(1);
        List<Item> list = Arrays.asList(one, new Item(2), new Item(3), new Item(4), new Item(5));
        TestSubscriber<Item> subscriber = TestSubscriber.create();
        Observable<Item> itemObservable = Observable.from(list);

        // when
        MathObservable.from(itemObservable)
            .min(Comparator.comparing(Item::getId))
            .subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(one);

    }

    class Item {
        private Integer id;

        public Item(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

    }
}
