package com.baeldung.selenium.visualregression.tests;

import com.baeldung.selenium.visualregression.pages.CameraProductPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SeleniumVisualRegressionTest extends BaseTest {

    private CameraProductPage cameraProductPage;

    @BeforeClass
    public void setup() {
        cameraProductPage = new CameraProductPage(this.driverManager.getDriver());
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

}
