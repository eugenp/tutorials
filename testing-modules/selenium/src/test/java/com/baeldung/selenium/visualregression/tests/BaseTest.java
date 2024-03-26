package com.baeldung.selenium.visualregression.tests;

import com.baeldung.selenium.visualregression.DriverManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected DriverManager driverManager;

    @BeforeSuite(alwaysRun = true)
    public void testSetup() {
        this.driverManager = new DriverManager();
        this.driverManager.startChromeInCloud();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        this.driverManager.quitDriver();
    }

}
