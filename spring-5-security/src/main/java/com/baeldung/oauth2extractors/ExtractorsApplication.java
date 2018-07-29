package com.baeldung.oauth2extractors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class ExtractorsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExtractorsApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "oauth2_extractors";
    }

}
