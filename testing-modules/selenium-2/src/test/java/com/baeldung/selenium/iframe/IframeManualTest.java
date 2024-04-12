package com.baeldung.selenium.iframe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class IframeManualTest {

    private WebDriver driver;
    private static final int TIMEOUT = 10;

    @BeforeMethod
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void whenSwitchToFrameUsingWebElement_thenSwitched() {
        openDemoPage();
        WebElement iframeElement = driver.findElement(By.cssSelector("#myFrame2"));

        new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
        waitForTextInFrame();
    }

    @Test
    public void whenSwitchToFrameUsingName_thenSwitched() {
        openDemoPage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frameName2"));
        waitForTextInFrame();
    }

    @Test
    public void whenSwitchToFrameUsingId_thenSwitched() {
        openDemoPage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("myFrame2"));
        waitForTextInFrame();
    }

    @Test
    public void whenSwitchToInnerFrameFromOuter_thenSwitched() {
        openPageWithNestedFrames();
        switchToNestedFrame();
        waitForLinkInNestedFrame();
    }

    @Test
    public void whenSwitchToParentFrame_thenSwitched() {
        openPageWithNestedFrames();
        switchToNestedFrame();
        driver.switchTo().parentFrame();
        waitForTextInParentFrame();
    }

    @Test
    public void whenSwitchToDefaultContent_thenSwitched() {
        openPageWithNestedFrames();
        switchToNestedFrame();
        driver.switchTo().defaultContent();
        waitForElementInDefaultContent();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    private void openDemoPage() {
        driver.get("https://seleniumbase.io/demo_page");
        new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#myForm")));
    }

    private void openPageWithNestedFrames() {
        driver.get("https://seleniumbase.io/w3schools/iframes");
        new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#runbtn")));
    }

    private void switchToNestedFrame() {
        driver.switchTo().frame("iframeResult");
        WebElement innerFrame = driver.findElement(By.cssSelector("[src='./demo_iframe.htm']"));
        driver.switchTo().frame(innerFrame);
    }

    private void waitForTextInFrame() {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
          .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("h4"), "iFrame Text"));
    }

    private void waitForLinkInNestedFrame() {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
          .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[href='https://seleniumbase.io/w3schools/iframes.html']")));
    }

    private void waitForTextInParentFrame() {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
          .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("h2"), "HTML Iframes (nested iframes)"));
    }

    private void waitForElementInDefaultContent() {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
          .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#runbtn")));
    }
}
