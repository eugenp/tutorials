package com.baeldung.dapr.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.dapr.spring.workflows.config.EnableDaprWorkflows;

@EnableDaprWorkflows
@SpringBootApplication
public class DaprWorkflowApp {

    public static void main(String[] args) {
        SpringApplication.run(DaprWorkflowApp.class, args);
    }
}
