package com.baeldung.threadsafety.tests;

import com.baeldung.threadsafety.services.ReentrantReadWriteLockCounter;
import com.baeldung.threadsafety.threads.ThreadH;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReentrantReadWriteLockCounterTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws InterruptedException {
        ReentrantReadWriteLockCounter counter = new ReentrantReadWriteLockCounter();
        ThreadH thread1 = new ThreadH(counter);
        ThreadH thread2 = new ThreadH(counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(thread1.getReentrantReadWriteLockCounter().getCounter()).isEqualTo(2);
        assertThat(thread2.getReentrantReadWriteLockCounter().getCounter()).isEqualTo(2);
    }
}


