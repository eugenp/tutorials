package com.baeldung.rxjava.combine;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rx.Observable;

public class ObservableMergeTest {

    @Test
    public void testMerge() {
        final List<String> results = new ArrayList<>();
        Observable.merge(
            Observable.from(new String[] {"Hello", "World"}),
            Observable.from(new String[]{ "I love", "RxJava"})
        ).subscribe(data -> {
           results.add(data); 
        });
        assertFalse(results.isEmpty());
        assertEquals(4, results.size());
        assertThat(results, hasItems("Hello", "World", "I love", "RxJava"));
    }
}
