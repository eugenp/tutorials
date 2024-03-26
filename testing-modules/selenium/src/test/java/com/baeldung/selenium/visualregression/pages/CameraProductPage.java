package com.baeldung.selenium.visualregression.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class CameraProductPage {
    private final WebDriver driver;

    public CameraProductPage(final WebDriver driver) {
        this.driver = driver;
    }

    private static final String SCREEN_NAME = "Camera-Product-Page";

    public void checkVisual() {
        ((JavascriptExecutor) this.driver).executeScript(MessageFormat.format("smartui.takeScreenshot={0}", SCREEN_NAME));
    }

}
