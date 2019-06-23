package com.baeldung.hexagonalarchitecture.adapter.api;

import com.baeldung.hexagonalarchitecture.domain.AvatarService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GithubAvatarEndpoint {
    private final AvatarService avatarService;

    GithubAvatarEndpoint(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @RequestMapping(value = "/avatar/user/{userName}", method = RequestMethod.GET)
    AvatarJson getUsersAvatarLink(@PathVariable String userName) {
        return AvatarJson.fromDomain(avatarService.getAvatarLink(userName));
    }
}
