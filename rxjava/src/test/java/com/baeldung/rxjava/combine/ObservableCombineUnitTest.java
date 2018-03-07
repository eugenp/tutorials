package com.baeldung.rxjava.combine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rx.Observable;

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
    public void testMerge() {
        List<String> results = new ArrayList<>();

        //@formatter:off
        Observable.merge(
          Observable.from(new String[] {"Hello", "World"}),
          Observable.from(new String[]{ "I love", "RxJava"})
        ).subscribe(data -> {
          results.add(data); 
        });
        //@formatter:on

        assertFalse(results.isEmpty());
        assertEquals(4, results.size());
        
        assertThat(results).contains("Hello", "World", "I love", "RxJava");
    }

    @Test
    public void testStartWith() {
        StringBuffer buffer = new StringBuffer();

        //@formatter:off
        Observable
          .from(new String[] { "RxJava", "Observables" })
          .startWith("Buzzwords of Reactive Programming")
          .subscribe(data -> {
            buffer.append(data).append(" ");
          });
        //@formatter:on

        assertEquals("Buzzwords of Reactive Programming RxJava Observables", buffer.toString().trim());
    }

    @Test
    public void testZip() {
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
        
        assertFalse(zippedStrings.isEmpty());
        assertEquals(3, zippedStrings.size());
        assertThat(zippedStrings).contains("Simple Solutions", "Moderate Success", "Complex Heirarchy");
    }
    
    @Test(expected = RuntimeException.class)
    public void testMergeDelayError() {
        List<String> results = new ArrayList<>();
        
        Future<String> f1 = executor.submit(createCallable("Hello"));
        Future<String> f2 = executor.submit(createCallable("World"));
        Future<String> f3 = executor.submit(createCallable(null));
        Future<String> f4 = executor.submit(createCallable("RxJava"));
        
        //@formatter:off
        Observable.mergeDelayError(
          Observable.from(f1),
          Observable.from(f2),
          Observable.from(f3),
          Observable.from(f4)
        ).subscribe(data -> {
          results.add(data);
        }, error -> {
          throw new RuntimeException(error);
        });
        //@formatter:on
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
