package com.baeldung.selenium.visualregression.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class CameraProductPage {
    private static String SCREEN_NAME = "Camera-Product-Page";
    private WebDriver driver;

    public CameraProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void checkVisual() {
        ((JavascriptExecutor) this.driver).executeScript(MessageFormat.format("smartui.takeScreenshot={0}", SCREEN_NAME));
    }
}