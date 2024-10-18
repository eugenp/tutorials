package com.baeldung.selenium.automatedbrowsertesting;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class SeleniumBrowserTestingOnCloud extends BaseTest{

    @Test
    public void testSearchProduct() {

        getDriver().get("https://ecommerce-playground.lambdatest.io/");
        WebElement searchBox = getDriver().findElement(By.name("search"));

        String searchText = "iPhone";
        searchBox.sendKeys(searchText);
        WebElement searchBtn = getDriver().findElement(By.cssSelector("button.type-text"));
        searchBtn.click();

        String pageHeader = getDriver().findElement(By.tagName("h1"))
            .getText();

        assertEquals(pageHeader, "Search - " + searchText);
    }



}
