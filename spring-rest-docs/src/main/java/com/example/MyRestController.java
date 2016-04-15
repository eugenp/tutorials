package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api")
public class MyRestController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "Hello";
    }

}
