package org.baeldung.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/helloGet")
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "hello";
    }

    @RequestMapping(value = "/helloPost", method = RequestMethod.POST)
    public String helloPost(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "helloPost";
    }
}
