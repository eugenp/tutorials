package com.baeldung;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature",
        plugin = {"pretty", "html:target/cucumber-reports"})
public class CucumberIntegrationTest {
}