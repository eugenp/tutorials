package com.baeldung.selenium.stale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RobustWebDriver implements WebDriver {

    private final WebDriver originalWebDriver;

    public RobustWebDriver(WebDriver webDriver) {
        this.originalWebDriver = webDriver;
    }

    @Override
    public void get(String url) {
        this.originalWebDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return this.originalWebDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return this.originalWebDriver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.originalWebDriver.findElements(by)
                .stream().map(e -> new RobustWebElement(e, by, this))
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        return new RobustWebElement(this.originalWebDriver.findElement(by), by, this);
    }

    @Override
    public String getPageSource() {
        return this.originalWebDriver.getPageSource();
    }

    @Override
    public void close() {
        this.originalWebDriver.close();

    }

    @Override
    public void quit() {
        this.originalWebDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return this.originalWebDriver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return this.originalWebDriver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return this.originalWebDriver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return this.originalWebDriver.navigate();
    }

    @Override
    public Options manage() {
        return this.originalWebDriver.manage();
    }
}