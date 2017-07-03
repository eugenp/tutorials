package baeldung.com;

import org.testng.annotations.Test;

class TimeOutIntegrationTest {

    @Test(timeOut = 1000, enabled = false)
    public void givenExecution_takeMoreTime_thenFail() {
        while (true) ;
    }
}
