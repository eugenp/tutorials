package com.baeldung.rxjava.combine;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.Observable;

public class ObservableMergeDelayErrorTest {

    private ExecutorService executor;
    
    @Before
    public void setup() {
        this.executor = Executors.newFixedThreadPool(10);
    }
    
    @After
    public void teardown() {
        this.executor.shutdown();
    }
    
    @Test(expected = RuntimeException.class)
    public void testMergeDelayError() {
        final List<String> results = new ArrayList<>();
        
        Future<String> f1 = executor.submit(new CallableByRx("First"));
        Future<String> f2 = executor.submit(new CallableByRx("Second"));
        Future<String> f3 = executor.submit(new CallableByRx(null));
        Future<String> f4 = executor.submit(new CallableByRx("Third"));
        
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
        
        assertFalse(results.isEmpty());
        assertEquals(3, results.size());
        assertThat(results, hasItems("First", "Second", "Third"));
    }
    
    static class CallableByRx implements Callable<String> {
        private String data;
        
        public CallableByRx(String data) {
            this.data = data;
        }

        @Override
        public String call() throws Exception {
            if (data == null) {
                throw new IllegalArgumentException("Data should not be null");
            }
            return data.toLowerCase();
        }
    }
}
