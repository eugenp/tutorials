package com.baeldun.selenium.testng;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestSeleniumWithTestNG {

    private WebDriver webDriver;
    private final String url = "http://www.baeldung.com/";
    private final String expectedTitle = "Baeldung | Java, Spring and Web Development tutorials";

    @BeforeSuite
    public void setUp() {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
    }

    @AfterSuite
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
