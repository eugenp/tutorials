package com.baeldung.rxjava.operators;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RxAggregateOperatorsUnitTest {

    @Test
    public void givenTwoObservable_whenConcatenatingThem_thenSuccessfull() {
        // given
        List<Integer> listOne = Arrays.asList(1, 2, 3, 4);
        Observable<Integer> observableOne = Observable.from(listOne);

        List<Integer> listTwo = Arrays.asList(5, 6, 7, 8);
        Observable<Integer> observableTwo = Observable.from(listTwo);

        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        Observable<Integer> concatObservable = observableOne.concatWith(observableTwo);

        concatObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(8);
        subscriber.assertValues(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    public void givenObservable_whenCounting_thenObtainingNumberOfElements() {
        // given
        List<String> lettersList = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        // when
        Observable<Integer> sourceObservable = Observable.from(lettersList)
          .count();
        sourceObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(7);
    }

    @Test
    public void givenObservable_whenReducing_thenObtainingInvertedConcatenatedString() {
        // given
        List<String> list = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

        TestSubscriber<String> subscriber = TestSubscriber.create();

        // when
        Observable<String> reduceObservable = Observable.from(list)
          .reduce((letter1, letter2) -> letter2 + letter1);
        reduceObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue("GFEDCBA");
    }

    @Test
    public void givenObservable_whenCollecting_thenObtainingASet() {
        // given
        List<String> list = Arrays.asList("A", "B", "C", "B", "B", "A", "D");

        TestSubscriber<HashSet> subscriber = TestSubscriber.create();

        // when
        Observable<HashSet<String>> reduceListObservable = Observable.from(list)
          .collect(HashSet::new, HashSet::add);
        reduceListObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValues(new HashSet<>(list));
    }

    @Test
    public void givenObservable_whenUsingToList_thenObtainedAList() {
        // given
        Observable<Integer> sourceObservable = Observable.range(1, 5);
        TestSubscriber<List> subscriber = TestSubscriber.create();

        // when
        Observable<List<Integer>> listObservable = sourceObservable.toList();
        listObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(Arrays.asList(1, 2, 3, 4, 5));
    }

    @Test
    public void givenObservable_whenUsingToSortedList_thenObtainedASortedList() {
        // given
        Observable<Integer> sourceObservable = Observable.range(10, 5);
        TestSubscriber<List> subscriber = TestSubscriber.create();

        // when
        Observable<List<Integer>> listObservable = sourceObservable.toSortedList();
        listObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(Arrays.asList(10, 11, 12, 13, 14));
    }

    @Test
    public void givenObservable_whenUsingToSortedListWithComparator_thenObtainedAnInverseSortedList() {
        // given
        Observable<Integer> sourceObservable = Observable.range(10, 5);
        TestSubscriber<List> subscriber = TestSubscriber.create();

        // when
        Observable<List<Integer>> listObservable = sourceObservable.toSortedList((int1, int2) -> int2 - int1);
        listObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(Arrays.asList(14, 13, 12, 11, 10));
    }

    @Test
    public void givenObservable_whenUsingToMap_thenObtainedAMap() {
        // given
        Observable<Book> bookObservable = Observable
          .just(
            new Book("The North Water", 2016),
            new Book("Origin", 2017),
            new Book("Sleeping Beauties", 2017));
        TestSubscriber<Map> subscriber = TestSubscriber.create();

        // when
        Observable<Map<String, Integer>> mapObservable = bookObservable
          .toMap(Book::getTitle, Book::getYear, HashMap::new);

        mapObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(new HashMap() {
            {
                put("The North Water", 2016);
                put("Origin", 2017);
                put("Sleeping Beauties", 2017);
            }
        });
    }

    @Test
    public void givenObservable_whenUsingToMultiMap_thenObtainedAMultiMap() {
        // given
        Observable<Book> bookObservable = Observable
          .just(
            new Book("The North Water", 2016),
            new Book("Origin", 2017),
            new Book("Sleeping Beauties", 2017));
        TestSubscriber<Map> subscriber = TestSubscriber.create();

        // when
        Observable multiMapObservable = bookObservable
          .toMultimap(Book::getYear, Book::getTitle, () -> new HashMap<>(), (key) -> new ArrayList<>());

        multiMapObservable.subscribe(subscriber);

        // then
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(new HashMap() {
            {
                put(2016, Arrays.asList("The North Water"));
                put(2017, Arrays.asList("Origin", "Sleeping Beauties"));
            }
        });
    }

    class Book {
        private String title;
        private Integer year;

        public Book(String title, Integer year) {
            this.title = title;
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public Integer getYear() {
            return year;
        }
    }
}
