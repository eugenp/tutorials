package com.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.services.IHomeService;

@Controller
public class HomeController {

    @Autowired
    IHomeService homeService;

    @GetMapping("/home")
    @ResponseBody
    public String greeting() {

        return homeService.getGreeting();
    }
}
