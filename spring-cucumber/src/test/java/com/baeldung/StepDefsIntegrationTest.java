package com.baeldung;

import com.baeldung.common.HeaderSettingRequestCallback;
import com.baeldung.common.ResponseResultErrorHandler;
import com.baeldung.common.ResponseResults;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@Import(ConfigIntegrationTest.class)
public class StepDefsIntegrationTest {

    static ResponseResults latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    void execute(String url, HttpMethod method) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, method, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }

    void executeGet(String url) throws IOException {
        execute(url, HttpMethod.GET);
    }

    void executePost() throws IOException {
        execute("http://localhost:8082/baeldung", HttpMethod.POST);
    }

    @When("^the client calls /baeldung$")
    public void the_client_issues_POST_hello() throws Throwable {
        executePost();
    }

    @Given("^the client calls /hello$")
    public void the_client_issues_GET_hello() throws Throwable {
        executeGet("http://localhost:8082/hello");
    }

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable {
        executeGet("http://localhost:8082/version");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        assertThat(latestResponse.getBody(), is(version));
    }
}