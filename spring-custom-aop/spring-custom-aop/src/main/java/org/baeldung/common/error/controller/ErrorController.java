package org.baeldung.common.error.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    public ErrorController() {
    }

    @RequestMapping("/400")
    String error400() {
        return "Error Code: 400 occured.";
    }

    @RequestMapping("/errorHeaven")
    String errorHeaven() {
        return "You have reached the heaven of errors!!!";
    }

}
