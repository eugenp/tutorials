package com.baeldung.synchronizedcollections.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class SynchronizedSetUnitTest {
    
    @Test
    public void givenSynchronizedSet_whenTwoThreadsAddElements_thenCorrectSetSize() throws InterruptedException {
        Set<Integer> syncSet = Collections.synchronizedSet(new HashSet<>());
        
        Runnable setOperations = () -> {syncSet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));};
        Thread thread1 = new Thread(setOperations);
        Thread thread2 = new Thread(setOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(syncSet.size()).isEqualTo(6);
    }
}
