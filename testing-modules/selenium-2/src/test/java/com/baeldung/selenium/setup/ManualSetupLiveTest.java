package com.baeldung.selenium.setup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

final class ManualSetupLiveTest {

    private static final String TITLE_XPATH = "//a[@href='/']";
    private static final String URL = "https://www.baeldung.com";

    private WebDriver driver;

    private void setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        options();
    }

    private void setupGeckoDriver() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        options();
    }

    private void setupEdgeDriver() {
        System.setProperty("webdriver.edge.driver", "src/test/resources/msedgedriver.exe");
        driver = new EdgeDriver();
        options();
    }

    private void options() {
        driver.manage()
          .window()
          .maximize();
    }

    @Test
    void givenChromeDriver_whenNavigateToBaeldung_thenFindTitleIsSuccessful() {
        setupChromeDriver();
        driver.get(URL);
        final WebElement title = driver.findElement(By.xpath(TITLE_XPATH));

        assertEquals("Baeldung", title.getAttribute("title"));
    }

    @Test
    void givenEdgeDriver_whenNavigateToBaeldung_thenFindTitleIsSuccessful() {
        setupEdgeDriver();
        driver.get(URL);
        final WebElement title = driver.findElement(By.xpath(TITLE_XPATH));

        assertEquals("Baeldung", title.getAttribute("title"));
    }

    @Test
    void givenGeckoDriver_whenNavigateToBaeldung_thenFindTitleIsSuccessful() {
        setupGeckoDriver();
        driver.get(URL);
        final WebElement title = driver.findElement(By.xpath(TITLE_XPATH));

        assertEquals("Baeldung", title.getAttribute("title"));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
