package com.baeldung.autowiring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.baeldung.autowiring.service.MyService;

@Controller
public class CorrectController {
    
    @Autowired
    MyService myService;
    
    public String control() {
        return myService.serve();
    }

}
