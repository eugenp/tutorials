package com.baeldung.selenium.automatedbrowsertesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Mp3PlayersPage {

    private WebDriver driver;

    public Mp3PlayersPage (WebDriver driver) {
        this.driver = driver;
    }

    public String pageHeader () {
        return driver.findElement (By.cssSelector (".content-title h1"))
            .getText ();
    }

}
