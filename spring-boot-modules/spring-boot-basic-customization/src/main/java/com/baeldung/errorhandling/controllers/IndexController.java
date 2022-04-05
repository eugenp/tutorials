package com.baeldung.errorhandling.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", ""})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/server_error"})
    public String triggerServerError() {
        "ser".charAt(30);
        return "index";
    }

    @PostMapping(value = {"/general_error"})
    public String triggerGeneralError() {
        return "index";
    }

}
