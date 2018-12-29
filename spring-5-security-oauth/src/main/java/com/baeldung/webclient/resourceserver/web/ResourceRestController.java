package com.baeldung.webclient.resourceserver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceRestController {

    @GetMapping("/retrieve-resource")
    public String retrieveResource() {
        return "This is the resource!";
    }

}
