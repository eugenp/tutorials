package com.baeldung.cucumber.calculator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.cucumber.calculator.Calculator;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculatorRunSteps {

    private int total;

    private static Logger logger = LoggerFactory.getLogger(CalculatorRunSteps.class);
    private Calculator calculator;

    @Before
    private void init() {
        total = -999;

    }

    @Given("^I have a calculator$")
    public void i_have_a_calculator() throws Throwable {
        calculator = new Calculator();
    }

    @When("^I add (-?\\d+) and (-?\\d+)$")
    public void i_add_and(int num1, int num2) throws Throwable {
        total = calculator.add(num1, num2);
    }

    @Then("^the result should be (-?\\d+)$")
    public void the_result_should_be(int result) throws Throwable {
        Assert.assertThat(total, Matchers.equalTo(result));
    }

}
