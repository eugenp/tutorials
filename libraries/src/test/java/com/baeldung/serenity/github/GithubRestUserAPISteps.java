package com.baeldung.serenity.github;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thucydides.core.annotations.Step;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class GithubRestUserAPISteps {

    private String api;
    private GitHubUser resource;

    @Step("Given the github REST API for user profile")
    public void withUserProfileAPIEndpoint() {
        api = "https://api.github.com/users/%s";
    }

    @Step("When looking for {0} via the api")
    public void getProfileOfUser(String username) throws IOException {
        HttpResponse httpResponse = getGithubUserProfile(api, username);
        resource = retrieveResourceFromResponse(httpResponse, GitHubUser.class);
    }

    @Step("Then there should be a login field with value {0} in payload of user {0}")
    public void profilePayloadShouldContainLoginValue(String username) {
        assertThat(username, Matchers.is(resource.getLogin()));
    }

    private static <T> T retrieveResourceFromResponse(final HttpResponse response, final Class<T> clazz) throws IOException {
        final String jsonFromResponse = EntityUtils.toString(response.getEntity());
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    private static HttpResponse getGithubUserProfile(String api, String username) throws IOException {
        HttpUriRequest request = new HttpGet(String.format(api, username));
        return HttpClientBuilder.create().build().execute(request);
    }
}
