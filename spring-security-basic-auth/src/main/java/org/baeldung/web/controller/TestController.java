package org.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// copied to --> spring-security-rest-basic-auth
@Controller
public class TestController {

    public TestController() {
        super();
    }

    // API

    @RequestMapping("/permitAll")
    @ResponseBody
    public String permitAll() {
        return "Permit All";
    }

    @RequestMapping("/securityNone")
    @ResponseBody
    public String securityNone() {
        return "Security None";
    }

}
