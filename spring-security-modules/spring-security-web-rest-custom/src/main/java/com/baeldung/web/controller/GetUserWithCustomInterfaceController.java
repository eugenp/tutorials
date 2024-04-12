package com.baeldung.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.security.IAuthenticationFacade;

@RestController
public class GetUserWithCustomInterfaceController {

    private final IAuthenticationFacade authenticationFacade;

    public GetUserWithCustomInterfaceController(IAuthenticationFacade authenticationFacade) {
        super();
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/username5")
    public String currentUserNameSimple() {
        final Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

}
