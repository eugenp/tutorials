package com.baeldung.springsecuritymigration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @RequestMapping("/")
    public String home() {
        return "Home Page";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome User";
    }

    @RequestMapping("/user-dashboard")
    public String dashboard() {
        return "My Dashboard";
    }
}
