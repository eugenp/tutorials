package com.baeldung.cucumber.calculator;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    features={"classpath:features/calculator.feature", "classpath:features/calculator-scenario-outline.feature"}
    , plugin = { "pretty", "json:target/reports/json/calculator.json" }
    , glue = {"com.baeldung.cucumber.calculator"}
)
public class CalculatorTest {
}
