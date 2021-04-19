package com.baeldung.cucumber_tags.acceptance.api.steps;


import com.baeldung.cucumber_tags.acceptance.commonutil.ScenarioContextApi;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

public class HealthSteps {

    @Autowired
    private ScenarioContextApi context;


    @When("^I make a GET call on ([^\"]*)$")
    public void iMakeAGETCallOn(String path) {
        context.invokeHttpGet(path);
    }

    @When("^I make a POST call on ([^\"]*)$")
    public void iMakeAPOSTCallOn(String path) {
        context.invokeHttpPost(path, context.postBody);
    }

    @Then("^I should receive (\\d+) response status code$")
    public void iShouldReceiveStatusCodeResponse(int code) {
        context.response.then().statusCode(code);
    }

    @Then("^should receive a non-empty body$")
    public void shouldReceiveANonEmptyBody() {
        context.response.then().body(Matchers.notNullValue());
    }


}