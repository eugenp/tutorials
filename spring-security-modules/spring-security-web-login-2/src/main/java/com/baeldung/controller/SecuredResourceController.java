package com.baeldung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SecuredResourceController {

    @RequestMapping("/secured")
    public void secureResource(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("accessing secured resource");
    }

}
