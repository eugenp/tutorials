package com.baeldung.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/site")
public class SiteController {

    @RequestMapping(value = "/{firstValue}/{secondValue}", method = RequestMethod.GET)
    public String requestWithError(@PathVariable("firstValue") String firstValue,
                          @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }

    @RequestMapping(value = "/{firstValue}/{secondValue:.+}", method = RequestMethod.GET)
    public String requestWithRegex(@PathVariable("firstValue") String firstValue,
                                   @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }

    @RequestMapping(value = "/{firstValue}/{secondValue}/", method = RequestMethod.GET)
    public String requestWithSlash(@PathVariable("firstValue") String firstValue,
                                   @PathVariable("secondValue") String secondValue) {

        return firstValue + " - " + secondValue;
    }
}
