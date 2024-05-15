package com.baeldung.selenium.tabs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Base class for Selenium Tests. This class handles the WebDriver setup and configuration.
 * It takes care about closing all tabs except one after each test. After a test class it will close the browser.
 */
public class SeleniumTestBase {

    protected static WebDriver driver;
    protected static TabHelper tabHelper;

    private static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        options();
        tabHelper = new TabHelper(driver);
    }

    private static void options() {
        driver.manage().window().maximize();
    }

    /**
     * Initializes the ChromeDriver before all tests.
     */
    @BeforeAll
    public static void init() {
        setupChromeDriver();
    }

    /**
     * After each test all tabs except the current tab will be closed.
     */
    @AfterEach
    public void closeTabs() {
        tabHelper.closeAllTabsExceptCurrent();
    }

    /**
     * After all tests the browser will be closed.
     */
    @AfterAll
    public static void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}