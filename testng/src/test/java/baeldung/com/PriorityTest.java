package baeldung.com;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PriorityTest {

    private String testString = "10";
    private int testInt = 23;

    @Test(priority = 1)
    public void givenString_whenChangedToInt_thenCorrect() {
        Assert.assertTrue(Integer.valueOf(testString) instanceof Integer);
    }

    @Test(priority = 2)
    public void givenInt_whenChangedToString_thenCorrect() {
        Assert.assertTrue(String.valueOf(testInt) instanceof String);
    }

}
