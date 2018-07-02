package com.baeldung.rxjava2.converter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SyncToRxApiTest {

    private SyncToRxApi sample;

    @Before
    public void setUp() throws Exception {
        this.sample = new SyncToRxApi();
    }

    @After
    public void tearDown() throws Exception {
        sample = null;
    }

    @Test
    public void givenMemberJsonFile_whenSyncLoaded_thenReturnMemberAsFlowable() throws IOException {
        sample
          .getMemberListAsFlowable("members.json")
          .subscribe(member -> Assert.assertNotEquals("", member.getFirstName()),
            Throwable::getMessage,
            () -> System.out.println("Completed"));
    }

    @Test @Ignore
    public void getMemberListWithAntiPattern() throws IOException {
        sample
          .getMemberListAntiPattern("members.json")
          .subscribe(member -> Assert.assertNotEquals("", member.getFirstName()),
            Throwable::getMessage,
            () -> System.out.println("Completed"));
    }
}
