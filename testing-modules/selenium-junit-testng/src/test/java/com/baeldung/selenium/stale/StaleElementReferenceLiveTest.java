package com.baeldung.selenium.stale;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

final class StaleElementReferenceLiveTest {

    private static WebDriver driver;
    private static final int TIMEOUT = 10;

    private static final By LOCATOR_REFRESH = By.xpath("//a[.='click here']");
    private static final By LOCATOR_DYNAMIC_CONTENT = By.xpath(
            "(//div[@id='content']//div[@class='large-10 columns'])[1]");

    private static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        options();
    }

    private static void options() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
    }

    @BeforeEach
    public void init() {
        setupChromeDriver();
    }

    @Test
    void givenDynamicPage_whenRefreshingAndAccessingSavedElement_thenSERE() {
        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_content?with_content=static");
        final WebElement element = driver.findElement(LOCATOR_DYNAMIC_CONTENT);

        driver.findElement(LOCATOR_REFRESH).click();
        Assertions.assertThrows(StaleElementReferenceException.class, element::getText);
    }

    @Test
    void givenDynamicPage_whenRefreshingAndAccessingSavedElement_thenHandleSERE() {
        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_content?with_content=static");
        final WebElement element = driver.findElement(LOCATOR_DYNAMIC_CONTENT);

        if (!retryingFindClick(LOCATOR_REFRESH)) {
            Assertions.fail("Element is still stale after 5 attempts");
        }
        Assertions.assertDoesNotThrow(() -> retryingFindGetText(LOCATOR_DYNAMIC_CONTENT));
    }

    private boolean retryingFindClick(By locator) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 5) {
            try {
                driver.findElement(locator).click();
                result = true;
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(ex.getMessage());
            }
            attempts++;
        }
        return result;
    }

    private String retryingFindGetText(By locator) {
        String result = null;
        int attempts = 0;
        while (attempts < 5) {
            try {
                result = driver.findElement(locator).getText();
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(ex.getMessage());
            }
            attempts++;
        }
        return result;
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}