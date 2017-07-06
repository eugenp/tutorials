package com.baeldung.displayallbeans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.displayallbeans.model.Person;

@Controller
public class PersonController {
    @Autowired
    Person person;
    
    @RequestMapping("/getPerson")
    public @ResponseBody Person getPerson() {
        return person;
    }
}