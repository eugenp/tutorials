package com.baeldung.selenium.visualregression.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.baeldung.selenium.visualregression.DriverManager;
import com.baeldung.selenium.visualregression.pages.CameraProductPage;

public class SeleniumVisualRegressionLiveTest {

    private DriverManager driverManager;
    private CameraProductPage cameraProductPage;

    @BeforeClass(alwaysRun = true)
    public void testSetup() {
        this.driverManager = new DriverManager();
        this.driverManager.startChromeInCloud();
        this.cameraProductPage = new CameraProductPage(this.driverManager.getDriver());
    }

    @Test
    public void whenActualImageIsDifferentFromBaseline_thenItShouldShowDifference() {

        this.driverManager.getDriver()
                .get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=30");

        this.cameraProductPage.checkVisual();
    }

    @Test
    public void whenActualImageIsSameAsBaseline_thenItShouldNotShowAnyDifference() {
        this.driverManager.getDriver()
                .get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=33");
        this.cameraProductPage.checkVisual();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driverManager.quitDriver();
    }
}