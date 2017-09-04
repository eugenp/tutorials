package com.baeldung.displayallbeans.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baeldung.displayallbeans.service.FooService;

@Controller
public class FooController {
    @Autowired
    private FooService fooService;

    @RequestMapping(value = "/displayallbeans")
    public String getHeaderAndBody(Map<String, Object> model) {
        model.put("header", fooService.getHeader());
        model.put("message", fooService.getBody());
        return "displayallbeans";
    }
}
