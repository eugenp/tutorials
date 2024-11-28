package com.baeldung.selenium.automatedbrowsertesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    private WebDriver driver;

    public HomePage (WebDriver driver) {
        this.driver = driver;
    }

    private WebElement shopByCategoryMenu () {
        return driver.findElement (By.linkText ("Shop by Category"));
    }

    public void openShopByCategoryMenu () {
        shopByCategoryMenu ().click ();
    }

    private WebElement mp3PlayersMenu () {
        return driver.findElement (By.cssSelector ("#widget-navbar-217841 > ul > li:nth-child(5) > a"));
    }

    public Mp3PlayersPage navigateToMp3PlayersPage () {
        mp3PlayersMenu ().click ();
        return new Mp3PlayersPage (driver);
    }

}
