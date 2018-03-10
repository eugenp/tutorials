package com.baeldung.rxjava.combine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

public class ObservableCombineUnitTest {

    private static ExecutorService executor;
    
    @BeforeClass
    public static void setupClass() {
        executor = Executors.newFixedThreadPool(10);
    }

    @AfterClass
    public static void tearDownClass() {
        executor.shutdown();
    }

    @Test
    public void givenTwoObservables_whenMerged_shouldEmitCombinedResults() {
        List<String> results = new ArrayList<>();

        //@formatter:off
        Observable.merge(
          Observable.from(new String[] {"Hello", "World"}),
          Observable.from(new String[]{ "I love", "RxJava"})
        ).subscribe(data -> {
          results.add(data); 
        });
        //@formatter:on

        assertThat(results).isNotEmpty();
        assertThat(results.size()).isEqualTo(4);
        assertThat(results).contains("Hello", "World", "I love", "RxJava");
    }

    @Test
    public void givenAnObservable_whenStartWith_thenPrependEmittedResults() {
        StringBuffer buffer = new StringBuffer();

        //@formatter:off
        Observable
          .from(new String[] { "RxJava", "Observables" })
          .startWith("Buzzwords of Reactive Programming")
          .subscribe(data -> {
            buffer.append(data).append(" ");
          });
        //@formatter:on

        assertThat(buffer.toString().trim()).isEqualTo("Buzzwords of Reactive Programming RxJava Observables");
    }

    @Test
    public void givenTwoObservables_whenZipped_thenReturnCombinedResults() {
        List<String> zippedStrings = new ArrayList<>();

        //@formatter:off
        Observable.zip(
          Observable.from(new String[] { "Simple", "Moderate", "Complex" }), 
          Observable.from(new String[] { "Solutions", "Success", "Heirarchy"}),
        (str1, str2) -> {
          return str1 + " " + str2;
        }).subscribe(zipped -> {
          zippedStrings.add(zipped);
        });
        //formatter:on
        
        assertThat(zippedStrings).isNotEmpty();
        assertThat(zippedStrings.size()).isEqualTo(3);
        assertThat(zippedStrings).contains("Simple Solutions", "Moderate Success", "Complex Heirarchy");
    }
    
    @Test
    public void givenMutipleObservablesOneThrows_whenMerged_thenCombineBeforePropagatingError() {        
        Future<String> f1 = executor.submit(createCallable("Hello"));
        Future<String> f2 = executor.submit(createCallable("World"));
        Future<String> f3 = executor.submit(createCallable(null));
        Future<String> f4 = executor.submit(createCallable("RxJava"));
        
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        
        //@formatter:off
        Observable.mergeDelayError(
          Observable.from(f1),
          Observable.from(f2),
          Observable.from(f3),
          Observable.from(f4)
        ).subscribe(testSubscriber);
        //@formatter:on
        
        testSubscriber.assertValues("hello", "world", "rxjava");
        testSubscriber.assertError(ExecutionException.class);
    }
    
    private Callable<String> createCallable(final String data) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (data == null) {
                    throw new IllegalArgumentException("Data should not be null.");
                }
                return data.toLowerCase();
            }
        };
    }
}
