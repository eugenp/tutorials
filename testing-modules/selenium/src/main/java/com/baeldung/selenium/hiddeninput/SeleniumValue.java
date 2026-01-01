package com.baeldung.selenium.hiddeninput;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumValue {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage()
          .timeouts()
          .implicitlyWait(Duration.ofSeconds(5));
        driver.manage()
          .window()
          .maximize();

        driver.get("http://localhost:8080/index.html");

        driver.findElement(By.id("username"))
          .sendKeys("selenium_user");

        driver.findElement(By.id("password"))
          .sendKeys("secret123");

        driver.findElement(By.cssSelector("input[name='gender'][value='male']"))
          .click();

        driver.findElement(By.id("dob"))
          .sendKeys("15-08-2025");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('hiddenInput').value='input-from-selenium';");

        Thread.sleep(4000);

        driver.findElement(By.id("submitBtn"))
          .click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.urlContains("/submit"));

        Thread.sleep(4000);

        driver.quit();
    }

}
