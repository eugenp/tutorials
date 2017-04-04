package com.baeldung.java9.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;

public class ReactiveStreamsTest {

    private static final int ITEM_SIZE = 10;

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

    };

    @Test
    public void testReactiveStreamCount() {
        ArrayList<String> myList = new ArrayList<String>();
        for (int i = 0; i < ITEM_SIZE; i++) {
            myList.add("item" + i);
        }

        ReactiveStreams demo = new ReactiveStreams();
        int count = demo.countStream(myList);
        assertEquals(ITEM_SIZE, count);
    }

    @Test
    public void testReactiveStreamTime() {
        ArrayList<String> myList = new ArrayList<String>();
        for (int i = 0; i < ITEM_SIZE; i++) {
            myList.add("item" + i);
        }

        ReactiveStreams demo = new ReactiveStreams();
        demo.countStream(myList);
        // The runtime in seconds should be equal to the number of items.
        assertTrue(stopwatch.runtime(TimeUnit.SECONDS) >= ITEM_SIZE);
    }

}
