package com.baeldung.serenity.spring;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aiet
 */
@RequestMapping(value = "/konamicode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class KonamiCodeController {

    private final String classicCode = "↑↑↓↓←→←→BA";

    @GetMapping("/classic")
    public String classicCode() {
        return classicCode;
    }

    @GetMapping("/cheatable")
    public boolean cheatCheck(@RequestParam String cheatcode){
        return classicCode.equals(cheatcode);
    }

}
