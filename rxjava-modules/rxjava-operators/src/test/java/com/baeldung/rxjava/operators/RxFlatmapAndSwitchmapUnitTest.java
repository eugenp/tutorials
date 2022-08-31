package com.baeldung.rxjava.operators;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RxFlatmapAndSwitchmapUnitTest {
    @Test
    public void givenObservable_whenFlatmap_shouldAssertAllItemsReturned() {
        //given
        List<String> actualOutput = new ArrayList<>();
        final TestScheduler scheduler = new TestScheduler();
        final List<String> keywordToSearch = Arrays.asList("b", "bo", "boo", "book", "books");

        //when
        Observable.fromIterable(keywordToSearch)
          .flatMap(s -> Observable
            .just(s + " FirstResult", s + " SecondResult")
            .delay(10, TimeUnit.SECONDS, scheduler))
          .toList()
          .doOnSuccess(s -> actualOutput.addAll(s))
          .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);

        //then
        assertThat(actualOutput, hasItems("b FirstResult", "b SecondResult",
          "boo FirstResult", "boo SecondResult",
          "bo FirstResult", "bo SecondResult",
          "book FirstResult", "book SecondResult",
          "books FirstResult", "books SecondResult"));
    }

    @Test
    public void givenObservable_whenSwitchmap_shouldAssertLatestItemReturned() {
        //given
        List<String> actualOutput = new ArrayList<>();
        final TestScheduler scheduler = new TestScheduler();
        final List<String> keywordToSearch = Arrays.asList("b", "bo", "boo", "book", "books");

        //when
        Observable.fromIterable(keywordToSearch)
          .switchMap(s -> Observable
            .just(s + " FirstResult", s + " SecondResult")
            .delay(10, TimeUnit.SECONDS, scheduler))
          .toList()
          .doOnSuccess(s -> actualOutput.addAll(s))
          .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);

        //then
        assertEquals(2, actualOutput.size());
        assertThat(actualOutput, hasItems("books FirstResult", "books SecondResult"));
    }
}
