package com.baeldung.selenium.tabs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumOpenNewTabLiveTest {

    private WebDriver driver;
    private static final int TIMEOUT = 10;
    private static final int EXPECTED_TABS_COUNT = 2;

    @BeforeMethod
    public void initDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void whenUseTabsApiOpenWindow_thenNewTabOpened() {
        driver.switchTo().newWindow(WindowType.TAB);
        waitTabsCount(EXPECTED_TABS_COUNT);
    }

    @Test
    public void whenExecuteOpenWindowJsScript_thenNewTabOpened() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        waitTabsCount(EXPECTED_TABS_COUNT);
    }


    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }


    private void waitTabsCount(int tabsCount) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .withMessage("Tabs count should be: " + tabsCount)
                .until(ExpectedConditions.numberOfWindowsToBe(tabsCount));
    }

}
