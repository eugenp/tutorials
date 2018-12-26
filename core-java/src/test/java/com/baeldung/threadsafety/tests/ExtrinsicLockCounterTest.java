package com.baeldung.threadsafety.tests;

import com.baeldung.threadsafety.services.ExtrinsicLockCounter;
import com.baeldung.threadsafety.threads.ThreadF;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtrinsicLockCounterTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws InterruptedException {
        ExtrinsicLockCounter counter = new ExtrinsicLockCounter();
        ThreadF thread1 = new ThreadF(counter);
        ThreadF thread2 = new ThreadF(counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(thread1.getExtrinsicLockCounter().getCounter()).isEqualTo(2);
        assertThat(thread2.getExtrinsicLockCounter().getCounter()).isEqualTo(2);
    }
}
