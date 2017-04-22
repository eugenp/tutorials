package com.baeldung.serenity.github;

import net.thucydides.core.annotations.Step;
import org.apache.http.HttpResponse;
import org.hamcrest.Matchers;

import java.io.IOException;

import static com.baeldung.serenity.github.RetrieveUtil.getGithubUserProfile;
import static com.baeldung.serenity.github.RetrieveUtil.retrieveResourceFromResponse;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author aiet
 */
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

}
