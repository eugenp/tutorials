package com.baeldung.testconainers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class WebDriverContainerLiveTest {

    public static BrowserWebDriverContainer chrome
      = new BrowserWebDriverContainer()
        .withCapabilities(new ChromeOptions());

    @BeforeAll
    static void setup() {
        chrome.start();
    }

    @Test
    public void whenNavigatedToPage_thenHeadingIsInThePage() {
        RemoteWebDriver driver = chrome.getWebDriver();
        driver.get("http://example.com");
        String heading = driver.findElement(By.xpath("/html/body/div/h1"))
            .getText();
        assertEquals("Example Domain", heading);
    }

}
