package com.baeldung.prevent.commandline.application.runner.execution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationCommandLineRunnerApp {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCommandLineRunnerApp.class, args);
    }
}
