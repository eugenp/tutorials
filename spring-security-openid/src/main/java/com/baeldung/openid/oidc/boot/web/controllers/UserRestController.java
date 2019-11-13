package com.baeldung.openid.oidc.boot.web.controllers;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @GetMapping("/principal-info")
    public Map<String, Object> getUserDetails(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

}
