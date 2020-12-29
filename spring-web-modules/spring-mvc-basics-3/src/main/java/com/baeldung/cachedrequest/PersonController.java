package com.baeldung.cachedrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @PostMapping(value = "/person")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void printPerson(@RequestBody Person person) {

        System.out.println("In Demo Controller. Person " + "is : " + person);
    }

    @GetMapping(value = "/person")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void getPerson() {

        System.out.println("In Demo Controller get method.");
    }
}