package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        // Set ChromeDriver path if not in system PATH
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        WebDriver driver = new ChromeDriver();

        try {
            // Open local test page
            driver.get("http://localhost:8000/test.html");

            // Wait briefly for JSON to load
            Thread.sleep(2000); // 2 seconds

            // Capture JSON from <pre> element
            String json = driver.findElement(By.id("output")).getText();
            System.out.println("Captured JSON:\n" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
