package com.baeldung.jsonp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping()
    public String retrieveIndex() {
        return "index";
    }
}
