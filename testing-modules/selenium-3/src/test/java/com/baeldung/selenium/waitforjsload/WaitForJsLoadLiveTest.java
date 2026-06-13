package com.baeldung.selenium.waitforjsload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

class WaitForJsLoadLiveTest {

    private static final String PAGE_URL = "https://www.baeldung.com/contact";
    private static final Logger log = LoggerFactory.getLogger(WaitForJsLoadLiveTest.class);
    
    private WebDriver driver;
    private PageLoadHelper pageLoadHelper;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    @BeforeEach
    void setUp() {
        WebDriverManager manager = WebDriverManager.chromedriver();
        driver = manager.create();
        
        log.info("Driver Path: {}", manager.getDownloadedDriverPath());
        log.info("Driver Version: {}", manager.getDownloadedDriverVersion());
        pageLoadHelper = new PageLoadHelper(driver, TIMEOUT);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void whenWaitForDocumentReady_thenStateIsComplete() {
        driver.get(PAGE_URL);
        pageLoadHelper.waitForDocumentReady();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String readyState = (String) js.executeScript(
          "return document.readyState");
        assertEquals("complete", readyState);
    }

    @Test
    void whenWaitForJQuery_thenNoActiveRequests() {
        driver.get(PAGE_URL);
        pageLoadHelper.waitForJQueryToStart();
        pageLoadHelper.waitForJQueryToFinish();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean jQueryIsDone = (Boolean) js.executeScript(
          "return (typeof jQuery === 'undefined') || (jQuery.active === 0)");
        assertTrue(jQueryIsDone);
    }

    @Test
    void whenWaitForElementClickable_thenClickable() {
        driver.get(PAGE_URL);
        pageLoadHelper.waitForElementToBeClickable(
          By.tagName("button"));

        assertTrue(driver.findElement(By.tagName("button"))
          .isDisplayed());
    }

    @Test
    void whenWaitForAngularAppStable_thenStable() {
        driver.get(PAGE_URL);
        Boolean stable = pageLoadHelper.waitForModernAngularAppStable();

        assertEquals(true, stable);
    }
}
