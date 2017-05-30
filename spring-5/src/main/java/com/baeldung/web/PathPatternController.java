package com.baeldung.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathPatternController {

    @RequestMapping(value = "/test/{*id}")
    public String URIVariableHandler(@PathVariable String id) {
        return id;
    }
}
