package com.baeldung.selenium.iframe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.By.cssSelector;


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
        WebElement iframeElement = driver.findElement(cssSelector("#myFrame2"));

        new WebDriverWait(driver, ofSeconds(10))
          .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
        waitForTextInFrame();
    }

    @Test
    public void whenSwitchToFrameUsingName_thenSwitched() {
        openDemoPage();
        new WebDriverWait(driver, ofSeconds(10))
          .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frameName2"));
        waitForTextInFrame();
    }

    @Test
    public void whenSwitchToFrameUsingId_thenSwitched() {
        openDemoPage();
        new WebDriverWait(driver, ofSeconds(10))
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
        new WebDriverWait(driver, ofSeconds(10))
          .until(ExpectedConditions.visibilityOfElementLocated(cssSelector("#myForm")));
    }

    private void openPageWithNestedFrames() {
        driver.get("https://seleniumbase.io/w3schools/iframes");
        new WebDriverWait(driver, ofSeconds(10))
          .until(ExpectedConditions.visibilityOfElementLocated(cssSelector("#runbtn")));
    }

    private void switchToNestedFrame() {
        driver.switchTo().frame("iframeResult");
        WebElement innerFrame = driver.findElement(cssSelector("[src='./demo_iframe.htm']"));
        driver.switchTo().frame(innerFrame);
    }

    private void waitForTextInFrame() {
        new WebDriverWait(driver, ofSeconds(TIMEOUT))
          .until(ExpectedConditions.textToBePresentInElementLocated(cssSelector("h4"), "iFrame Text"));
    }

    private void waitForLinkInNestedFrame() {
        new WebDriverWait(driver, ofSeconds(TIMEOUT))
          .until(ExpectedConditions.presenceOfElementLocated(cssSelector("[href='https://seleniumbase.io/w3schools/iframes.html']")));
    }

    private void waitForTextInParentFrame() {
        new WebDriverWait(driver, ofSeconds(TIMEOUT))
          .until(ExpectedConditions.textToBePresentInElementLocated(cssSelector("h2"), "HTML Iframes (nested iframes)"));
    }

    private void waitForElementInDefaultContent() {
        new WebDriverWait(driver, ofSeconds(TIMEOUT))
          .until(ExpectedConditions.visibilityOfElementLocated(cssSelector("#runbtn")));
    }
}
