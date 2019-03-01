package com.baeldung.synchronizedcollections.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class SynchronizedCollectionUnitTest {

    @Test
    public void givenSynchronizedCollection_whenTwoThreadsAddElements_thenCorrectCollectionSize() throws InterruptedException {
        Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());

        Runnable listOperations = () -> {
            syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        };
        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(syncCollection.size()).isEqualTo(12);
    }
}
