package com.baeldung.reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathPatternController {

    @GetMapping("/spring5/{*id}")
    public String URIVariableHandler(@PathVariable String id) {
        return id;
    }

    @GetMapping("/s?ring5")
    public String wildcardTakingExactlyOneChar() {
        return "/s?ring5";
    }

    @GetMapping("/spring5/*id")
    public String wildcardTakingZeroOrMoreChar() {
        return "/spring5/*id";
    }

    @GetMapping("/resources/**")
    public String wildcardTakingZeroOrMorePathSegments() {
        return "/resources/**";
    }

    @GetMapping("/{baeldung:[a-z]+}")
    public String regexInPathVariable(@PathVariable String baeldung) {
        return baeldung;
    }

    @GetMapping("/{var1}_{var2}")
    public String multiplePathVariablesInSameSegment(@PathVariable String var1, @PathVariable String var2) {
        return "Two variables are var1=" + var1 + " and var2=" + var2;
    }
}
