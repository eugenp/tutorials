package com.baeldung;

import org.testng.annotations.Test;

public class TimeOutIntegrationTest {

    @Test(timeOut = 1000, enabled = false)
    public void givenExecution_takeMoreTime_thenFail() {
        while (true) ;
    }
}
