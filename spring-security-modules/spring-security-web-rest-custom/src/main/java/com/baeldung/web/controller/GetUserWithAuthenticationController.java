package com.baeldung.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetUserWithAuthenticationController {

    public GetUserWithAuthenticationController() {
        super();
    }

    // API

    @RequestMapping(value = "/username3", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(final Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Retrieved user with authorities: " + userDetails.getAuthorities());
        return authentication.getName();
    }

}
