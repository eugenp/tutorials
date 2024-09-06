package com.baeldung.selenium.alertsandpopups;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandlePopupsUnitTests {

    @Test
    public void whenBasicAuthPopupAppears_thenBypassWithCredentials() {
        WebDriver driver = new ChromeDriver();

        String username = "admin";
        String password = "admin";
        String url = "https://" + username + ":" + password + "@the-internet.herokuapp.com/basic_auth";

        driver.get(url);

        String bodyText = driver.findElement(By.tagName("body"))
            .getText();
        assertTrue(bodyText.contains("Congratulations! You must have the proper credentials."));

        driver.quit();
    }

    @Test
    public void whenModalDialogAppears_thenHandleIt() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/entry_ad");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement closeElement = driver.findElement(By.xpath("//div[@class='modal-footer']/p"));
        js.executeScript("arguments[0].click();", closeElement);

        String pageText = driver.findElement(By.tagName("body"))
            .getText();
        assertFalse(pageText.contains("This is a modal window"));

        driver.quit();
    }



}
