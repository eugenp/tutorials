package com.baeldung.passinglistascucumberparameter;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class LocalDateTransformer {
    private static final Logger logger = LoggerFactory.getLogger(LocalDateTransformer.class);

    @ParameterType("yyyy-MM-dd")
    public LocalDate localdate(String dateString) {
        return LocalDate.parse(dateString);
    }

    @Given("the user enters birthdate {localdate}")
    public void enterBirthdate(LocalDate birthdate) {
        logger.info("Birthdate: {}", birthdate);
    }
}
