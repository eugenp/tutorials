package com.baeldung.caffeine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CaffieneTutorialApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CaffieneTutorialApp.class).run(args);
    }
}
