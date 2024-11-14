package com.baeldung.selenium.visualregression;

import static java.text.MessageFormat.format;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {
    private WebDriver driver;

    public void startChromeInCloud()  {
        String ltUsername = System.getenv("LT_USERNAME");
        String ltAccessKey = System.getenv("LT_ACCESS_KEY");
        String gridUrl = "@hub.lambdatest.com/wd/hub";
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");
        browserOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        HashMap<String, Object> ltOptions = getLambdaTestOptions();
        browserOptions.setCapability("LT:Options", ltOptions);

        try {
            this.driver = new RemoteWebDriver(new URI(format("https://{0}:{1}{2}", ltUsername, ltAccessKey, gridUrl)).toURL(), browserOptions);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new Error("Error in setting RemoteDriver's URL!");
        }
        this.driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(20));
    }

    private static HashMap<String, Object> getLambdaTestOptions() {
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("resolution", "2560x1440");
        ltOptions.put("video", true);
        ltOptions.put("build", "smartui-demo");
        ltOptions.put("name", "visual regression with smartui");
        ltOptions.put("smartUI.project", "Visual Regression Selenium Demo");
        ltOptions.put("smartUI.baseline", false);
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        HashMap<String, Object> smartOptions = new HashMap<>();
        smartOptions.put("largeImageThreshold", 1200);
        smartOptions.put("transparency", 0.3);
        smartOptions.put("errorType", "movement");
        ltOptions.put("smartUI.options", smartOptions);
        return ltOptions;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void quitDriver() {
        this.driver.quit();
    }
}