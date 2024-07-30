package com.baeldung.passinglistascucumberparameter;

import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexLoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(RegexLoginSteps.class);

    @Given("the user enters email address \"([^\"]*)\"")
    public void enterEmailAddress(String emailAddress) {
        logger.info("Email: {}", emailAddress);
    }
}
