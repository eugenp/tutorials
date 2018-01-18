package com.baeldung.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Person;
import com.google.gson.Gson;

@RestController
public class PersonInfoController {

    @GetMapping("/personResource")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody String personInfo() {
        Gson gson = new Gson();
        String person = gson.toJson(new Person("abir", "Dhaka", "Bangladesh", 29, "Male"));
        return person;
    }
}