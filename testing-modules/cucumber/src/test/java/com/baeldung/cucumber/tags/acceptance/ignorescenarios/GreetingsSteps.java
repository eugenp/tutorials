package com.baeldung.cucumber.tags.acceptance.ignorescenarios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GreetingsSteps {
    private String hours;

    @LocalServerPort
    int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;

    @Given("the current time is {string} hours")
    public void theCurrentTimeIsXHour(String hours) {
        this.hours = hours;
    }

    @When("I ask the greeter to greet")
    public void askTheGreeterToGreet() throws Exception {
        String url = String.format("http://localhost:%s/greetings?hours=%s", port, hours);
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("I should receive {string}")
    public void theGreetingShouldBe(String expectedGreeting) {
        assertEquals(expectedGreeting, response.getBody());
    }
}
