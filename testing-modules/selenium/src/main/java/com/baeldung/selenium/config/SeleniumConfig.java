package com.baeldung.selenium.config;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumConfig {

    private WebDriver driver;

    public SeleniumConfig() {
        driver = new FirefoxDriver();
        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(5));
    }

    static {
        System.setProperty("webdriver.gecko.driver", findFile("geckodriver.mac"));
    }

    private static String findFile(String filename) {
        String[] paths = { "", "bin/", "target/classes" }; // if you have chromedriver somewhere else on the path, then put it here.
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    public void close() {
        driver.close();
    }

    public void navigateTo(String url) {
        driver.navigate()
            .to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public WebDriver getDriver() {
        return driver;
    }
}