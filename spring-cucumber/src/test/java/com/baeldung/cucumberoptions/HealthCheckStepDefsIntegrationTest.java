package com.baeldung.cucumberoptions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.baeldung.SpringIntegrationTest;

public class HealthCheckStepDefsIntegrationTest extends SpringIntegrationTest {

    private ResponseEntity<String> statusResponse;

    private ResponseEntity<String> doGet(String url) {
        return new RestTemplate().getForEntity(url, String.class);
    }

    @When("^the client calls /v1/status")
    public void checkV1Status() throws Throwable {
        statusResponse = doGet("http://localhost:8082/v1/status");
    }

    @When("^the client calls /v2/status")
    public void checkV2Status() throws Throwable {
        statusResponse = doGet("http://localhost:8082/v2/status");
    }

    @Then("^the client receives (\\d+) status code$")
    public void verifyStatusCode(int statusCode) throws Throwable {
        final HttpStatusCode currentStatusCode = statusResponse.getStatusCode();
        assertThat(currentStatusCode.value(), is(statusCode));
    }
}