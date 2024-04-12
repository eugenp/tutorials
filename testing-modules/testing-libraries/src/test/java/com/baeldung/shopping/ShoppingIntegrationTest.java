package com.baeldung.shopping;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/shopping.feature" })
public class ShoppingIntegrationTest {

}
