package com.baeldung.synchronizedcollections.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class SynchronizedListUnitTest {

    @Test
    public void givenSynchronizedList_whenTwoThreadsAddElements_thenCorrectListSize() throws InterruptedException {
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

        Runnable listOperations = () -> {
            syncList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        };
        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(syncList.size()).isEqualTo(12);
    }
    
    @Test
    public void givenStringList_whenTwoThreadsIterateOnSynchronizedList_thenCorrectResult() throws InterruptedException {
        List<String> syncCollection = Collections.synchronizedList(Arrays.asList("a", "b", "c"));
        List<String> uppercasedCollection = new ArrayList<>();

        Runnable listOperations = () -> {
            synchronized (syncCollection) {
                syncCollection.forEach((e) -> {
                    uppercasedCollection.add(e.toUpperCase());
                });
            }
        };
        
        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(uppercasedCollection.get(0)).isEqualTo("A");
    }
}
