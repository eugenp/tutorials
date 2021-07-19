package com.baeldung.maven.polyglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class MavenPolyglotApplication {
    public static void main(String[] args) {
        SpringApplication.run(MavenPolyglotApplication.class, args);
    }

    @GetMapping("/")
    public String home(){
        return "Hello JSON Maven Model !";
    }
}
