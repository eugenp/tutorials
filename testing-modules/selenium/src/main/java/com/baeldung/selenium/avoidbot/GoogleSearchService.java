package com.baeldung.selenium.avoidbot;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleSearchService {

    private final WebDriver driver;

    public GoogleSearchService(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToGoogle() {
        driver.get("https://www.google.com");
    }

    public void search(String query) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.sendKeys(Keys.ENTER);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void quit() {
        driver.quit();
    }
}

