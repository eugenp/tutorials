package com.baeldung.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserWithAuthenticationController {

    public GetUserWithAuthenticationController() {
        super();
    }

    @GetMapping(value = "/username3")
    public String currentUserNameSimple(final Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Retrieved user with authorities: " + userDetails.getAuthorities());
        return authentication.getName();
    }

}
