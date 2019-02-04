package com.baeldung.springbootsecuritycors.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ResourceController {

    @RequestMapping("/user")
    public String user(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }
}
