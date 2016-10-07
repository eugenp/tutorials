package test.java.com.baeldung.selenium.testng;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import main.java.com.baeldung.selenium.SeleniumExample;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestSeleniumWithTestNG {

    private SeleniumExample seleniumExample;
    private String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

    @BeforeSuite
    public void setUp() {
        seleniumExample = new SeleniumExample();
    }

    @AfterSuite
    public void tearDown() {
        seleniumExample.closeWindow();
    }

    @Test
    public void whenPageIsLoaded_thenTitleIsAsPerExpectation() {
        String actualTitle = seleniumExample.getTitle();
        assertNotNull(actualTitle);
        assertEquals(actualTitle, expectedTitle);
    }
}
