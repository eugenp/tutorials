package com.baeldung.threadsafety.tests;

import com.baeldung.threadsafety.services.Counter;
import com.baeldung.threadsafety.threads.ThreadE;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class CounterTest {
    
    @Test
    public void whenCalledIncremenCounter_thenCorrect() throws InterruptedException {
        Counter counter = new Counter();
        ThreadE thread1 = new ThreadE(counter);
        ThreadE thread2 = new ThreadE(counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(thread1.getCounter().getCounter()).isEqualTo(2);
        assertThat(thread2.getCounter().getCounter()).isEqualTo(2);
    }
}
