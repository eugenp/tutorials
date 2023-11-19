package com.baeldung.selenium.stale;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class RobustWebElement implements WebElement {

    private WebElement originalElement;
    private final RobustWebDriver driver;
    private final By by;

    private static final int MAX_RETRIES = 10;
    private static final String SERE = "Element is no longer attached to the DOM";

    public RobustWebElement(WebElement element, By by, RobustWebDriver driver) {
        this.originalElement = element;
        this.by = by;
        this.driver = driver;
    }

    @Override
    public void click() {
        executeMethodWithRetries(WebElement::click);
    }

    @Override
    public void submit() {
        executeMethodWithRetries(WebElement::submit);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        executeMethodWithRetriesVoid(WebElement::sendKeys, keysToSend);
    }

    @Override
    public void clear() {
        executeMethodWithRetries(WebElement::clear);
    }

    @Override
    public String getTagName() {
        return executeMethodWithRetries(WebElement::getTagName);
    }

    @Override
    public String getAttribute(String name) {
        return executeMethodWithRetries(WebElement::getAttribute, name);
    }

    @Override
    public boolean isSelected() {
        return executeMethodWithRetries(WebElement::isSelected);
    }

    @Override
    public boolean isEnabled() {
        return executeMethodWithRetries(WebElement::isEnabled);
    }

    @Override
    public String getText() {
        return executeMethodWithRetries(WebElement::getText);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return executeMethodWithRetries(WebElement::findElements, by);
    }

    @Override
    public WebElement findElement(By by) {
        return executeMethodWithRetries(WebElement::findElement, by);
    }

    @Override
    public boolean isDisplayed() {
        return executeMethodWithRetries(WebElement::isDisplayed);
    }

    @Override
    public Point getLocation() {
        return executeMethodWithRetries(WebElement::getLocation);
    }

    @Override
    public Dimension getSize() {
        return executeMethodWithRetries(WebElement::getSize);
    }

    @Override
    public Rectangle getRect() {
        return executeMethodWithRetries(WebElement::getRect);
    }

    @Override
    public String getCssValue(String propertyName) {
        return executeMethodWithRetries(WebElement::getCssValue, propertyName);
    }


    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return executeMethodWithRetries(WebElement::getScreenshotAs, target);
    }

    private void executeMethodWithRetries(Consumer<WebElement> method) {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                WebElementUtils.callMethod(originalElement, method);
                return;
            } catch (StaleElementReferenceException ex) {
                refreshElement();
            }
            retries++;
        }
        throw new StaleElementReferenceException(SERE);
    }

    private <T> T executeMethodWithRetries(Function<WebElement, T> method) {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return WebElementUtils.callMethodWithReturn(originalElement, method);
            } catch (StaleElementReferenceException ex) {
                refreshElement();
            }
            retries++;
        }
        throw new StaleElementReferenceException(SERE);
    }

    private <U> void executeMethodWithRetriesVoid(BiConsumer<WebElement, U> method, U parameter) {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                WebElementUtils.callMethod(originalElement, method, parameter);
                return;
            } catch (StaleElementReferenceException ex) {
                refreshElement();
            }
            retries++;
        }
        throw new StaleElementReferenceException(SERE);
    }

    private <T, U> T executeMethodWithRetries(BiFunction<WebElement, U, T> method, U parameter) {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return WebElementUtils.callMethodWithReturn(originalElement, method, parameter);
            } catch (StaleElementReferenceException ex) {
                refreshElement();
            }
            retries++;
        }
        throw new StaleElementReferenceException(SERE);
    }

    private void refreshElement() {
        this.originalElement = driver.findElement(by);
    }
}