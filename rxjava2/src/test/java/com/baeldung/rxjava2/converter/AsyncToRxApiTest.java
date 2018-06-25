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
    public void givenMemberJsonFile_whenAsyncLoaded_thenReturnMemberAsFlowable()
                                                        throws InterruptedException {
        this.sample
          .getMemberListAsyncAsFlowable()
          .subscribe(System.out::println,
                    Throwable::printStackTrace);
        Thread.sleep(2000);
    }
}