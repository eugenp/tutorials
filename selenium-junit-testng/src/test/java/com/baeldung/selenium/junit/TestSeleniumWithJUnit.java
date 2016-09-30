package test.java.com.baeldung.selenium.junit;

import static org.testng.Assert.assertEquals;
import main.java.com.baeldung.selenium.SeleniumExample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSeleniumWithJUnit {

    private SeleniumExample seleniumExample;

    @Before
    public void setUp() {
        seleniumExample = new SeleniumExample();
    }

    @After
    public void tearDown() {
        seleniumExample.closeWindow();
    }

    @Test
    public void whenPageIsLoaded_thenTitleIsAsPerExpectation() {
        String expectedTitle = seleniumExample.getExpectedTitle();
        String actualTitle = seleniumExample.getActualTitle();
        assertEquals(actualTitle, expectedTitle);
    }
}
