package com.baeldung.rxjava2.converter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AsyncToRxApiTest {

    private AsyncToRxApi sample;

    @Before
    public void setUp() throws Exception {
        this.sample = new AsyncToRxApi();
    }

    @After
    public void tearDown() throws Exception {
        this.sample = null;
    }

    @Test
    public void giventMemberListAsyncAsFlowable_whenSubscribing_thenWaitForCompleted()
      throws InterruptedException {
        this.sample
          .getMemberListAsyncAsFlowable()
          .subscribe(System.out::println,
                    Throwable::printStackTrace);
        Thread.sleep(2000);
    }
    
    @Test
    public void giventMemberListAsyncAsFlowable_whenSubscribing_thenLatchOnCompleted() 
      throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        this.sample
          .getMemberListAsyncAsFlowable()
          .subscribe(member -> System.out.println(member), Throwable::getMessage, () -> latch.countDown());
        latch.await();
    }
    
}
