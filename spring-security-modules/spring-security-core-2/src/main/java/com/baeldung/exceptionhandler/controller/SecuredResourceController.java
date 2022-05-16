package com.baeldung.exceptionhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secured")
public class SecuredResourceController {

    @GetMapping
    public String secureResource() {
        return "/admin.html";
    }
}
