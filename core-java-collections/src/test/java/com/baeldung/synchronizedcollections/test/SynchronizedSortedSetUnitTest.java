package com.baeldung.synchronizedcollections.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class SynchronizedSortedSetUnitTest {
    
    @Test
    public void givenSynchronizedSortedSet_whenTwoThreadsAddElements_thenCorrectSortedSetSize() throws InterruptedException {
        SortedSet<Integer> syncSortedSet = Collections.synchronizedSortedSet(new TreeSet<>());
        
        Runnable sortedSetOperations = () -> {syncSortedSet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));};
        sortedSetOperations.run();
        sortedSetOperations.run();
        Thread thread1 = new Thread(sortedSetOperations);
        Thread thread2 = new Thread(sortedSetOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(syncSortedSet.size()).isEqualTo(6);
    }
}
