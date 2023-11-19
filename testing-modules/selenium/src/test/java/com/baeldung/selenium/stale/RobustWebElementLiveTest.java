package com.baeldung.selenium.stale;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

final class RobustWebElementLiveTest {

    private static RobustWebDriver driver;
    private static final int TIMEOUT = 10;

    private static final By LOCATOR_REFRESH = By.xpath("//a[.='click here']");
    private static final By LOCATOR_DYNAMIC_CONTENT = By.xpath(
            "(//div[@id='content']//div[@class='large-10 columns'])[1]");

    private static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new RobustWebDriver(new ChromeDriver(options));
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
    void givenDynamicPage_whenRefreshingAndAccessingSavedElement_thenOK() {
        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_content?with_content=static");
        final WebElement element = driver.findElement(LOCATOR_DYNAMIC_CONTENT);

        driver.findElement(LOCATOR_REFRESH).click();
        Assertions.assertDoesNotThrow(element::getText);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}