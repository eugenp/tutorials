package com.baeldung.resttemplate.logging.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @PostMapping("/persons")
    public List<String> getPersons() {
        return Arrays.asList(new String[] { "Lucie", "Jackie", "Danesh", "Tao" });
    }

}