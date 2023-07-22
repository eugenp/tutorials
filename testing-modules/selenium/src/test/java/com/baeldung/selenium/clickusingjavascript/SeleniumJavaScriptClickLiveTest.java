package com.baeldung.selenium.clickusingjavascript;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SeleniumJavaScriptClickLiveTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", new File("src/main/resources/chromedriver.mac").getAbsolutePath());
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS));
    }

    @After
    public void cleanUp() {
        driver.close();
    }

    @Test
    public void whenSearchForSeleniumArticles_thenReturnNotEmptyResults() {
        driver.get("https://baeldung.com");
        String title = driver.getTitle();
        assertEquals("Baeldung", title);

        wait.until(ExpectedConditions.elementToBeClickable(By.className("nav--menu_item_anchor")));
        WebElement searchButton = driver.findElement(By.className("nav--menu_item_anchor"));
        clickElement(searchButton);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-40489")));
        WebElement searchInput = driver.findElement(By.id("menu-item-40489"));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".nav--menu_item")));
        WebElement seeSearchResultsButton = driver.findElement(By.cssSelector(".nav--menu_item"));
        clickElement(seeSearchResultsButton);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("nav--menu_item_anchor")));
        int seleniumPostsCount = driver.findElements(By.className("nav--menu_item_anchor"))
            .size();
        assertTrue(seleniumPostsCount > 0);
    }

    private void clickElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

}