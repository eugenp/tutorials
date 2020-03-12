package com.baeldung.guava.mapmaker;

import com.google.common.collect.MapMaker;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertNotNull;

public class GuavaMapMakerUnitTest {
        @Test public void whenMakeMap_thenCreated() {
                ConcurrentMap<String, String> concurrentMap = new MapMaker().makeMap();
                assertNotNull(concurrentMap);
        }

        @Test public void whenMakeMapWithWeakKeys_thenCreated() {
                ConcurrentMap<String, String> concurrentMap = new MapMaker().weakKeys().makeMap();
                assertNotNull(concurrentMap);
        }

        @Test public void whenMakeMapWithWeakValues_thenCreated() {
                ConcurrentMap<String, String> concurrentMap = new MapMaker().weakValues().makeMap();
                assertNotNull(concurrentMap);
        }

        @Test public void whenMakeMapWithInitialCapacity_thenCreated() {
                ConcurrentMap<String, String> concurrentMap = new MapMaker().initialCapacity(10).makeMap();
                assertNotNull(concurrentMap);
        }

        @Test public void whenMakeMapWithConcurrencyLevel_thenCreated() {
                ConcurrentMap<String, String> concurrentMap = new MapMaker().concurrencyLevel(10).makeMap();
                assertNotNull(concurrentMap);
        }
}
