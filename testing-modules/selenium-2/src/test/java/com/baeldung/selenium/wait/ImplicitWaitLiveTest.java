package com.baeldung.selenium.wait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

final class ImplicitWaitLiveTest {

    private static WebDriver driver;
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
    }

    @BeforeEach
    public void init() {
        setupChromeDriver();
    }

    @Test
    void givenPage_whenNavigatingWithImplicitWait_ThenOK() {
        final String expected = "About Baeldung";
        driver.navigate().to("https://www.baeldung.com/");

        driver.findElement(LOCATOR_ABOUT).click();
        driver.findElement(LOCATOR_ABOUT_BAELDUNG).click();

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