package com.baeldung.scrollelementintoview;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.net.URL;

public class ScrollElementIntoView {

    private WebDriver driver;

    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        URL formUrl = getClass().getClassLoader().getResource("form.html");
        if (formUrl != null) {
            driver.get(formUrl.toString());
        } else {
            throw new RuntimeException("form.html not found in resources");
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void scrollToElementCenter(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
          "const rect = arguments[0].getBoundingClientRect();" +
          "window.scrollBy({ top: rect.top + window.pageYOffset - (window.innerHeight / 2) + (rect.height / 2), behavior: 'smooth' });",
          element
        );
    }

    public void runDemo() throws InterruptedException {
        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement middleName = driver.findElement(By.id("middleName"));
        WebElement lastName = driver.findElement(By.id("lastName"));

        scrollToElementCenter(firstName);
        Thread.sleep(1000);
        firstName.sendKeys("John");

        scrollToElementCenter(middleName);
        Thread.sleep(1000);
        middleName.sendKeys("William");

        scrollToElementCenter(lastName);
        Thread.sleep(1000);
        lastName.sendKeys("Doe");

        Thread.sleep(2000);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void main(String[] args) {
        ScrollElementIntoView demo = new ScrollElementIntoView();
        try {
            demo.setUp();
            demo.runDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            demo.tearDown();
        }
    }
}
