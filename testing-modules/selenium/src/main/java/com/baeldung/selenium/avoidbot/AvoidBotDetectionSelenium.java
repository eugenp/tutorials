package com.baeldung.selenium.avoidbot;

import com.baeldung.selenium.avoidbot.GoogleSearchService;
import org.openqa.selenium.chrome.ChromeDriver;

public class AvoidBotDetectionSelenium {

    public static void main(String[] args) {
        ChromeDriver driver = WebDriverFactory.createDriver();
        GoogleSearchService googleService = new GoogleSearchService(driver);
        googleService.navigateToGoogle();
        googleService.search("baeldung");
        googleService.quit();
    }
}
