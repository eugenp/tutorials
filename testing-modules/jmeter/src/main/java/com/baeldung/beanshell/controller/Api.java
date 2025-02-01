package com.baeldung.beanshell.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.beanshell.model.Item;

@RestController
@RequestMapping("api")
public class Api {

    @PostMapping
    public String post(@RequestBody Item body) {
        return body.getId()
            .toString();
    }
}
