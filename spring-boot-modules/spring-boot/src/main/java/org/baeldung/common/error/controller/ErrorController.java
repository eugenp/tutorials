package org.baeldung.common.error.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    public ErrorController() {
    }

    @GetMapping("/400")
    String error400() {
        return "Error Code: 400 occured.";
    }

    @GetMapping("/errorHaven")
    String errorHeaven() {
        return "You have reached the haven of errors!!!";
    }

}
