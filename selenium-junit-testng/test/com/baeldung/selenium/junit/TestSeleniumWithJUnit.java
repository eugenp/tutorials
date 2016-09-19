package com.baeldung.selenium.junit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestSeleniumWithJUnit {

    private WebDriver webDriver;
    private final String url = "http://www.baeldung.com/";
    private final String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

    @Before
    public void setUp() {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
    }

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void whenPageIsLoaded_thenTitleIsAsPerExpectation() {
        String actualTitleReturned = webDriver.getTitle();
        assertNotNull(actualTitleReturned);
        assertEquals(expectedTitle, actualTitleReturned);
    }
}
