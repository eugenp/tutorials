package com.baeldung.context1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.contexts.IHomeService;

@Controller
public class Context1Controller {

    @Autowired
    IHomeService homeService;

    @GetMapping("/home")
    @ResponseBody
    public String greeting() {

        return homeService.getGreeting();
    }
}