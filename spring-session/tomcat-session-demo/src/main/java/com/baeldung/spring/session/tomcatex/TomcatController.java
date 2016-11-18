package com.baeldung.spring.session.tomcatex;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TomcatController {
    @RequestMapping("/tomcat/admin")
    public String helloTomcatAdmin() {
        return "hello tomcat admin";
    }
}
