package com.baeldung.selenium.find;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindElementByAttributeManualTest {

    private WebDriver driver;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    @BeforeClass
    public void init() {
        driver = new ChromeDriver();
    }

    @Test
    public void whenSearchByAttributeName_thenFindAllElementsWithAttribute() {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[draggable]"), 2));
    }

    @Test
    public void whenSearchByAttributeExactValue_thenFindExactMatchElement() {
        driver.get("https://the-internet.herokuapp.com/forgot_password");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[type='submit']")));
    }

    @Test
    public void whenSearchByAttributeStartValue_thenFindStartValueMatchElement() {
        driver.get("https://the-internet.herokuapp.com/download");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[href^='download/']"), 0));
    }

    @Test
    public void whenSearchByAttributeEndValue_thenFindEndValueMatchElement() {
        driver.get("https://the-internet.herokuapp.com/download");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[href$='.pdf']"), 0));
    }

    @Test
    public void whenSearchByAttributeContainsValue_thenFindContainsValueMatchElement() {
        driver.get("https://the-internet.herokuapp.com/upload");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id*='le-up']")));
    }

    @Test
    public void whenSearchByAttributeClassValue_thenFindClassValueMatchElement() {
        driver.get("https://the-internet.herokuapp.com/upload");

        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class*='dz-clickable']")));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
