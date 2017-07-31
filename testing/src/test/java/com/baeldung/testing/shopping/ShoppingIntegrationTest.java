package com.baeldung.testing.shopping;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/shopping.feature" })
public class ShoppingIntegrationTest {

}
