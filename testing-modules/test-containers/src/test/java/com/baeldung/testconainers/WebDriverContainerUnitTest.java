package com.baeldung.testconainers;

import static org.junit.Assert.assertEquals;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.GenericContainer;

public class WebDriverContainerUnitTest {
    @Rule
    public BrowserWebDriverContainer chrome
      = new BrowserWebDriverContainer()
        .withDesiredCapabilities(DesiredCapabilities.chrome());

    @Test
    public void whenNavigatedToPage_thenHeadingIsInThePage() {
        RemoteWebDriver driver = chrome.getWebDriver();
        driver.get("https://saucelabs.com/test/guinea-pig");
        String heading = driver.findElement(By.xpath("/html/body/h1"))
            .getText();
        assertEquals("This page is a Selenium sandbox", heading);
    }
    
}
