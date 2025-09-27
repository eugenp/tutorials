package com.baeldung.selenium.avoidbot;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    public static ChromeDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        ChromeDriver driver = new ChromeDriver(options);
        Map<String, Object> params = new HashMap<>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);

        return driver;
    }
}

