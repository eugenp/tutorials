package com.baeldung.calculator;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = {"classpath:features/calculator.feature", "classpath:features/calculator-scenario-outline.feature"}
  , plugin = {"pretty", "json:target/reports/json/calculator.json"}
  , glue = {"com.baeldung.cucumber.calculator"}
)
public class CalculatorIntegrationTest {
}
