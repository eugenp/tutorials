package com.baeldung.webclient.resourceserver.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceRestController {

    @GetMapping("/retrieve-resource")
    public String retrieveResource() {
        return "This is the resource!";
    }
    
    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

}
