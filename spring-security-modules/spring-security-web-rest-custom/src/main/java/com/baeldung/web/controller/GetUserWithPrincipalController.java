package com.baeldung.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserWithPrincipalController {

    public GetUserWithPrincipalController() {
        super();
    }

    @GetMapping(value = "/username2")
    public String currentUserName(final Principal principal) {
        return principal.getName();
    }

}
