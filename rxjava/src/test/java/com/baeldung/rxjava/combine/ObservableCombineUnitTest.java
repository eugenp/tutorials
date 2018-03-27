package com.baeldung.rxjava.combine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

public class ObservableCombineUnitTest {

    @Test
    public void givenTwoObservables_whenMerged_shouldEmitCombinedResults() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        //@formatter:off
        Observable.merge(
          Observable.from(new String[] {"Hello", "World"}),
          Observable.from(new String[]{ "I love", "RxJava"})
        ).subscribe(testSubscriber);
        //@formatter:on

        testSubscriber.assertValues("Hello", "World", "I love", "RxJava");
    }

    @Test
    public void givenTwoObservables_whenZipped_thenReturnCombinedResults() {
        List<String> zippedStrings = new ArrayList<>();

        //@formatter:off
        Observable.zip(
          Observable.from(new String[] { "Simple", "Moderate", "Complex" }), 
          Observable.from(new String[] { "Solutions", "Success", "Heirarchy"}),
        (str1, str2) -> str1 + " " + str2).subscribe(zippedStrings::add);
        //formatter:on
        
        assertThat(zippedStrings).isNotEmpty();
        assertThat(zippedStrings.size()).isEqualTo(3);
        assertThat(zippedStrings).contains("Simple Solutions", "Moderate Success", "Complex Heirarchy");
    }
    
    @Test
    public void givenMutipleObservablesOneThrows_whenMerged_thenCombineBeforePropagatingError() {        
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        
        Observable.mergeDelayError(
          Observable.from(new String[] { "hello", "world" }),
          Observable.error(new RuntimeException("Some exception")),
          Observable.from(new String[] { "rxjava" })
        ).subscribe(testSubscriber);

        testSubscriber.assertValues("hello", "world", "rxjava");
        testSubscriber.assertError(RuntimeException.class);;
    }
    
    @Test
    public void givenAStream_whenZippedWithInterval_shouldDelayStreamEmmission() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
            
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");
        Observable<Long> interval = Observable.interval(1L, TimeUnit.SECONDS);
            
        Observable
          .zip(data, interval, (strData, tick) -> String.format("[%d]=%s", tick, strData))
          .toBlocking().subscribe(testSubscriber);
            
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(5);
        testSubscriber.assertValues("[0]=one", "[1]=two", "[2]=three", "[3]=four", "[4]=five");
    }
}
