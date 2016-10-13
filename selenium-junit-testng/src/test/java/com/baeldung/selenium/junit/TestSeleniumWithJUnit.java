package test.java.com.baeldung.selenium.junit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import main.java.com.baeldung.selenium.SeleniumExample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSeleniumWithJUnit {

    private SeleniumExample seleniumExample;
	private String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

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
        String actualTitle = seleniumExample.getTitle();
        assertNotNull(actualTitle);
        assertEquals(actualTitle, expectedTitle);
    }
}
