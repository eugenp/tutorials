package com.baeldung.PassingListAsCucumberParameter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    @Given("the user enters username {string} and password {string}")
    public void enterUsernameAndPassword(String username, String password) {
        System.out.println("Username: " + username + ", Password: " + password);
    }

    @When("the user clicks on the login button")
    public void clickLoginButton() {
        System.out.println("Login button clicked");
    }

    @Then("the dashboard should be displayed")
    public void verifyDashboardDisplayed() {
        System.out.println("Dashboard displayed");
    }
}
