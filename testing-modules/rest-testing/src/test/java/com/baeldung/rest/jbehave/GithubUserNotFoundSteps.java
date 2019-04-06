package com.baeldung.rest.jbehave;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertTrue;

public class GithubUserNotFoundSteps {

    private String api;
    private String nonExistentUser;
    private int githubResponseCode;

    @Given("github user profile api")
    public void givenGithubUserProfileApi() {
        api = "https://api.github.com/users/%s";
    }

    @Given("a random non-existent username")
    public void givenANonexistentUsername() {
        nonExistentUser = randomAlphabetic(8);
    }

    @When("I look for the random user via the api")
    public void whenILookForTheUserViaTheApi() throws IOException {
        githubResponseCode = getGithubUserProfile(api, nonExistentUser)
          .getStatusLine()
          .getStatusCode();
    }

    @When("I look for $user via the api")
    public void whenILookForSomeNonExistentUserViaTheApi(String user) throws IOException {
        githubResponseCode = getGithubUserProfile(api, user)
          .getStatusLine()
          .getStatusCode();
    }

    static HttpResponse getGithubUserProfile(String api, String username) throws IOException {
        HttpUriRequest request = new HttpGet(String.format(api, username));
        return HttpClientBuilder
          .create()
          .build()
          .execute(request);
    }

    @Then("github respond: 404 not found")
    public void thenGithubRespond404NotFound() {
        assertTrue(SC_NOT_FOUND == githubResponseCode);
    }
}
