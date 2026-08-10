package com.baeldung.selenium.waitforjsload;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageLoadHelper {

    private final WebDriverWait wait;

    public PageLoadHelper(WebDriver driver, Duration timeout) {
        this.wait = new WebDriverWait(driver, timeout);
    }

    public void waitForDocumentReady() {
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript("return document.readyState")
              .equals("complete");
        });
    }
    
    public void waitForJQueryToStart() {
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript(
              "return (typeof jQuery === 'undefined') || (jQuery.active > 0)");
        });
    }

    public void waitForJQueryToFinish() {
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript(
              "return (typeof jQuery === 'undefined') || (jQuery.active === 0)");
        });
    }

    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public Boolean waitForModernAngularAppStable() {
        return wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = """
              return (typeof getAllAngularTestabilities === 'undefined' ||
                      getAllAngularTestabilities().every(t => t.isStable()))
              """;
            return (Boolean) js.executeScript(script);
        });
    }

    public void waitForJsAppReady() {
        waitForDocumentReady();
        
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript(
              """
              return (typeof window.appReady === 'undefined') || (window.appReady === true)
              """);
        });
    }
}
