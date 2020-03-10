package com.baeldung.guava.mapmaker;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.MapMaker;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

public class GuavaMapMakerUnitTest {
        @Test
        public void whenMakeMap_thenCreated() {
                ConcurrentMap<String, String> m = new MapMaker()
                    .makeMap();
                assertNotNull(m);
        }

        @Test
        public void whenMakeMapWithWeakKeys_thenCreated() {
                ConcurrentMap<String, String> m = new MapMaker()
                    .weakKeys()
                    .makeMap();
                assertNotNull(m);
        }

        @Test
        public void whenMakeMapWithWeakValues_thenCreated() {
                ConcurrentMap<String, String> m = new MapMaker()
                    .weakValues()
                    .makeMap();
                assertNotNull(m);
        }

        @Test
        public void whenMakeMapWithInitialCapacity_thenCreated() {
                ConcurrentMap<String, String> m = new MapMaker()
                    .initialCapacity(10)
                    .makeMap();
                assertNotNull(m);
        }

        @Test
        public void whenMakeMapWithConcurrencyLevel_thenCreated() {
                ConcurrentMap<String, String> m = new MapMaker()
                    .concurrencyLevel(10)
                    .makeMap();
                assertNotNull(m);
        }
}
