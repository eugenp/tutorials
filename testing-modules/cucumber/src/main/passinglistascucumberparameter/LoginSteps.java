package com.baeldung.passinglistascucumberparameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    @Given("the user enters username {string} and password {string}")
    public void enterUsernameAndPassword(String username, String password) {
        logger.info("Username: {}, Password: {}", username, password);
    }

    @When("the user clicks on the login button")
    public void clickLoginButton() {
        logger.info("Login button clicked");
    }

    @Then("the dashboard should be displayed")
    public void verifyDashboardDisplayed() {
        logger.info("Dashboard displayed");
    }
}
