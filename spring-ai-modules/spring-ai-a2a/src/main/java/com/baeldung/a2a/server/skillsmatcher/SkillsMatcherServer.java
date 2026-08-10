package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-skills-matcher-server.properties")
public class SkillsMatcherServer {

    public static void main(String[] args) {
        SpringApplication.run(SkillsMatcherServer.class, args);
    }
}