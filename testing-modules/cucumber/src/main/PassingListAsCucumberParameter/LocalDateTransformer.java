package com.baeldung.PassingListAsCucumberParameter;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class LocalDateTransformer {
    @ParameterType("yyyy-MM-dd")
    public LocalDate localdate(String dateString) {
        return LocalDate.parse(dateString);
    }

    @Given("the user enters birthdate {localdate}")
    public void enterBirthdate(LocalDate birthdate) {
        System.out.println("Birthdate: " + birthdate);
    }
}
