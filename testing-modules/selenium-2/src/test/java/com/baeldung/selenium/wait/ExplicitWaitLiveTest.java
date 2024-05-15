package com.baeldung.selenium.wait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

final class ExplicitWaitLiveTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final int TIMEOUT = 10;

    private static final By LOCATOR_ABOUT = By.xpath("//a[starts-with(., 'About')]");
    private static final By LOCATOR_ABOUT_BAELDUNG = By.xpath("//h3[normalize-space()='About Baeldung']");
    private static final By LOCATOR_ABOUT_HEADER = By.xpath("//h1");

    private static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        options();
    }

    private static void options() {
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void init() {
        setupChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    @Test
    void givenPage_whenNavigatingWithoutExplicitWait_thenElementNotInteractable() {
        driver.navigate().to("https://www.baeldung.com/");
        WebElement about = driver.findElement(LOCATOR_ABOUT_BAELDUNG);
        assertThrows(ElementNotInteractableException.class, about::click);
    }

    @Test
    void givenPage_whenNavigatingWithExplicitWait_thenOK() {
        final String expected = "About Baeldung";
        driver.navigate().to("https://www.baeldung.com/");

        driver.findElement(LOCATOR_ABOUT).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_ABOUT_BAELDUNG));

        driver.findElement(LOCATOR_ABOUT_BAELDUNG).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_ABOUT_HEADER));

        final String actual = driver.findElement(LOCATOR_ABOUT_HEADER).getText();
        assertEquals(expected, actual);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}