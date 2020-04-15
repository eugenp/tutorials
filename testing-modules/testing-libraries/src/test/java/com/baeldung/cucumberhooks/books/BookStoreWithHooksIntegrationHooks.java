package com.baeldung.cucumberhooks.books;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java8.En;

public class BookStoreWithHooksIntegrationHooks implements En {

    public BookStoreWithHooksIntegrationHooks() {
        Before(1, () -> startBrowser());
    }

    @Before(order=2, value="@Screenshots")
    public void beforeScenario(Scenario scenario) {
        takeScreenshot();
    }

    @After
    public void afterScenario(Scenario scenario) {
        takeScreenshot();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        takeScreenshot();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        takeScreenshot();
        closeBrowser();
    }

    public void takeScreenshot() {
        //code to take and save screenshot
    }

    public void startBrowser() {
        //code to open browser
    }

    public void closeBrowser() {
        //code to close browser
    }
}
