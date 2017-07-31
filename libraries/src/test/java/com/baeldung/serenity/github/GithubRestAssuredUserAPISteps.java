package com.baeldung.serenity.github;

import net.thucydides.core.annotations.Step;
import org.hamcrest.Matchers;

import java.io.IOException;

import static net.serenitybdd.rest.SerenityRest.rest;
import static net.serenitybdd.rest.SerenityRest.then;

class GithubRestAssuredUserAPISteps {

    private String api;

    @Step("Given the github REST API for user profile")
    void withUserProfileAPIEndpoint() {
        api = "https://api.github.com/users/{username}";
    }

    @Step("When looking for {0} via the api")
    void getProfileOfUser(String username) throws IOException {
        rest().get(api, username);
    }

    @Step("Then there should be a login field with value {0} in payload of user {0}")
    void profilePayloadShouldContainLoginValue(String username) {
        then().body("login", Matchers.equalTo(username));
    }

}
