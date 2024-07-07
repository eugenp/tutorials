package com.baeldung.PassingListAsCucumberParameter;

import io.cucumber.java.en.Given;

public class RegexLoginSteps {
    @Given("the user enters email address \"([^\"]*)\"")
    public void enterEmailAddress(String emailAddress) {
        System.out.println("Email: " + emailAddress);
    }
}
