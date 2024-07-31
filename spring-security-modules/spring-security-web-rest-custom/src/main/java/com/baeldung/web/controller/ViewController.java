package com.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({ "/index", "/" })
    public String index() {
        return "index";
    }
    
    @RequestMapping({ "/runashome" })
    public String run() {
        return "runas";
    }
}
