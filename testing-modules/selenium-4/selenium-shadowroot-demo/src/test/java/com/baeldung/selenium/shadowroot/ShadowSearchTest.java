package com.baeldung.selenium.shadowroot;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
// Comment out the next line for running Selenium 3 test
import org.openqa.selenium.NoSuchShadowRootException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShadowSearchTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void googleSearchFromShadowDom() throws InterruptedException, UnsupportedEncodingException {
        try {
            // Sets up driver wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Loads local HTML file
            URL htmlUrl = getClass().getClassLoader().getResource("shadow-sample.html");
            driver.get(htmlUrl.toString());
            String shadowSampleUrl = driver.getCurrentUrl();

            // 1. Finds shadow host
            WebElement shadowHost = driver.findElement(By.cssSelector("#shadow-host"));

            // 2. Finds shadow root
            SearchContext shadowRoot = wait.until(driver -> {
                try {
                    return shadowHost.getShadowRoot();
                } catch (NoSuchShadowRootException e) {
                    return null;
                }
            });

            // *2. Finds shadow root - the legacy way
            /*
           JavascriptExecutor js = (JavascriptExecutor) driver;
            SearchContext shadowRoot = wait.until(driver -> {
                return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
            });
            */

            // 3, 4. Finds input element and inputs query
            WebElement searchField = shadowRoot.findElement(By.cssSelector("#search-input"));
            searchField.sendKeys("shadow dom");
            TimeUnit.SECONDS.sleep(2);

            // 5, 6. Finds submit button and clicks it
            WebElement searchButton = shadowRoot.findElement(By.cssSelector("#search-button"));
            searchButton.click();

            // 7. Waits for navigation
            wait.until(driver -> !driver.getCurrentUrl().equals(shadowSampleUrl));
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Uncomment after switching to Selenium 3 and commenting out the test above
    /*
    @Test
    void googleSearchFromShadowDomWithSelenium3() throws InterruptedException {
        try {
            // Sets up
            WebDriverWait wait = new WebDriverWait(driver, 10);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Loads local HTML file
            URL htmlUrl = getClass().getClassLoader().getResource("shadow-sample.html");
            driver.get(htmlUrl.toString());
            String shadowSampleUrl = driver.getCurrentUrl();

            // 1. Finds shadow host
            WebElement shadowHost = driver.findElement(By.cssSelector("#shadow-host"));

            // 2, 3. Finds search input via shadow root
            WebElement searchField = wait.until(driver -> {
                return (WebElement) js.executeScript(
                        "return arguments[0].shadowRoot.querySelector('#search-input')",
                        shadowHost
                );
            });

            // 4. Inputs query
            searchField.sendKeys("shadow dom");
            TimeUnit.SECONDS.sleep(2);

            // 2, 5. Finds search button via shadow root
            WebElement searchButton = wait.until(driver -> {
                return (WebElement) js.executeScript(
                        "return arguments[0].shadowRoot.querySelector('#search-button')",
                        shadowHost
                );
            });

            // 6. Clicks search button
            searchButton.click();

            // 7. Waits for navigation
            wait.until(driver -> !driver.getCurrentUrl().equals(shadowSampleUrl));
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    */
}
