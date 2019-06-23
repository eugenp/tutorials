package com.baeldung.hexagonalarchitecture.adapter.github;

import com.baeldung.hexagonalarchitecture.domain.Avatar;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class GithubUserResponse {

    private final String avatarUrl;

    @JsonCreator
    GithubUserResponse(@JsonProperty("avatar_url") String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    Avatar asDomain() {
        return new Avatar(avatarUrl);
    }
}
