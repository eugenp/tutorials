package com.baeldung.cucumber;

import com.baeldung.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private Account account;

    @Given("account balance is {double}")
    public void account_balance_is(Double initialBalance) {
        account = new Account(initialBalance);
    }

    @When("the account is credited with {double}")
    public void the_account_is_credited_with(Double amount) {
        account.credit(amount);
    }

    @Then("account should have a balance of {double}")
    public void account_should_have_a_balance_of(Double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance());
    }
}
