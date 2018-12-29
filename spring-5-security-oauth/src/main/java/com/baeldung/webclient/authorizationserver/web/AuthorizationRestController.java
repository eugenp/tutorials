package com.baeldung.webclient.authorizationserver.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationRestController {
    
    @GetMapping("/user/bael-user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

}
