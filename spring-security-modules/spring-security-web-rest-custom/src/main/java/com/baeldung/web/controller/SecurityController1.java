package com.baeldung.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController1 {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public SecurityController1() {
        super();
    }

    // API

    @RequestMapping(value = "/username1", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            final String currentPrincipalName = authentication.getName();
            System.out.println("Authentication: " + authentication);
            System.out.println("Principal: " + authentication.getPrincipal());
            return currentPrincipalName;
        }

        return null;
    }

}
