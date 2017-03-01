package baeldung.com;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MultiThreadedTests {

    @Test(threadPoolSize = 5, invocationCount = 10, timeOut = 1000)
    public void givenMethod_whenRunInThreads_thenCorrect() {
        int count = Thread.activeCount();
        Assert.assertTrue(count > 1);
    }

}
