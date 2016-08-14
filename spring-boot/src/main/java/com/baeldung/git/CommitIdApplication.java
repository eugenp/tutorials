package com.baeldung.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/git.properties")
@SpringBootApplication(scanBasePackages = { "com.baeldung.git"})
public class CommitIdApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommitIdApplication.class, args);
    }
}

