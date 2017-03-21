package baeldung.com;

import org.testng.annotations.Test;

public class TimeOutTest {

    @Test(timeOut = 1000, enabled = false)
    public void givenExecution_takeMoreTime_thenFail() {
        while (true) ;
    }
}
