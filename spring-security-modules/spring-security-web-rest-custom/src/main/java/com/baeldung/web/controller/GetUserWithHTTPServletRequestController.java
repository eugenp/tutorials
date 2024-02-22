package com.baeldung.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class GetUserWithHTTPServletRequestController {

    public GetUserWithHTTPServletRequestController() {
        super();
    }

    @GetMapping(value = "/username4")
    public String currentUserNameSimple(final HttpServletRequest request) {
        final Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

}
