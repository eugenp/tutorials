package com.baeldung.pathvariable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class SiteController {

    @GetMapping("/{firstValue}/{secondValue}")
    public String requestWithError(@PathVariable("firstValue") String firstValue,
                          @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }

    @GetMapping("/{firstValue}/{secondValue:.+}")
    public String requestWithRegex(@PathVariable("firstValue") String firstValue,
                                   @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }

    @GetMapping("/{firstValue}/{secondValue}/")
    public String requestWithSlash(@PathVariable("firstValue") String firstValue,
                                   @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }
}
