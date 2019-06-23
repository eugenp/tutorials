package com.baeldung.hexagonalarchitecture.adapter.api;

import com.baeldung.hexagonalarchitecture.domain.Avatar;
import com.fasterxml.jackson.annotation.JsonProperty;

class AvatarJson {

    @JsonProperty("link") private String link;

    AvatarJson(String link) {
        this.link = link;
    }

    String getLink() {
        return link;
    }

    static AvatarJson fromDomain(Avatar avatarLink) {
        return new AvatarJson(avatarLink.getLink());
    }
}
