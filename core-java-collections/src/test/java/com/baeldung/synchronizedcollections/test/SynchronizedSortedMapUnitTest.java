package com.baeldung.synchronizedcollections.test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class SynchronizedSortedMapUnitTest {
    
    @Test
    public void givenSynchronizedSorteMap_whenTwoThreadsAddElements_thenCorrectSortedMapSize() throws InterruptedException {
        Map<Integer, String> syncSortedMap = Collections.synchronizedSortedMap(new TreeMap<>());
        
        Runnable sortedMapOperations = () -> {
            syncSortedMap.put(1, "One");
            syncSortedMap.put(2, "Two");
            syncSortedMap.put(3, "Three");
        };
        Thread thread1 = new Thread(sortedMapOperations);
        Thread thread2 = new Thread(sortedMapOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(syncSortedMap.size()).isEqualTo(3);
    }
}
