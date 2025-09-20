package com.baeldung.selenium.avoidbotDetectionSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class AvoidBotDetectionSelenium {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        ChromeDriver driver = new ChromeDriver(options);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);
        driver.get("https://www.google.com");
        System.out.println("Navigated to Google's homepage.");

        WebElement searchBox = driver.findElement(By.name("q"));
        System.out.println("Found the search box.");

        searchBox.sendKeys("baeldung");
        System.out.println("Entered 'baeldung' into the search box.");

        searchBox.sendKeys(Keys.ENTER);
        System.out.println("Submitted the search query.");
        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }
}
