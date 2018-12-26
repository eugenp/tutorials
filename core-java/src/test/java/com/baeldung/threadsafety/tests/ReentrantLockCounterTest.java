package com.baeldung.threadsafety.tests;

import com.baeldung.threadsafety.services.ReentrantLockCounter;
import com.baeldung.threadsafety.threads.ThreadG;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReentrantLockCounterTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws InterruptedException {
        ReentrantLockCounter counter = new ReentrantLockCounter();
        ThreadG thread1 = new ThreadG(counter);
        ThreadG thread2 = new ThreadG(counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(thread1.getReentrantLockCounter().getCounter()).isEqualTo(2);
        assertThat(thread2.getReentrantLockCounter().getCounter()).isEqualTo(2);
    }
}
