package com.baeldung.rxjava2.converter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

class RxToRxApiUnitTest {

    RxToRxApi sample;

    @Before
    public void setUp() throws Exception {
        this.sample = new RxToRxApi();
    }

    @After
    public void tearDown() throws Exception {
        sample = null;
    }

    @Test
    public void givenMemberJsonFile_whenRxLoaded_thenReturnMemberAsFlowable()
      throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        sample
          .getMembersListAsFlowable("src/test/resources/members.json")
          .subscribe(
            member -> System.out.println(member),
            Throwable::getMessage,
            () -> latch.countDown());
        latch.await();
    }

    @Test
    void givenMemberJsonArray_whenRxLoaded_thenReturnMemberAsFlowable()
      throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        sample
          .getMemberRxArrayAsFlowable("src/test/resources/members.json")
          .subscribe(
            member -> System.out.println(member),
            Throwable::getMessage,
            () -> latch.countDown());
        latch.await();
    }

}