package com.baeldung.dapr.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication.Running;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testcontainers.Testcontainers;

import com.baeldung.dapr.workflow.config.DaprWorkflowTestConfig;

@SpringBootApplication
public class DaprWorkflowsTestApp {

    public static void main(String[] args) {
        Running app = SpringApplication.from(DaprWorkflowApp::main)
            .with(DaprWorkflowTestConfig.class)
            .run(args);

        int port = app.getApplicationContext()
            .getEnvironment()
            .getProperty("server.port", Integer.class);
        Testcontainers.exposeHostPorts(port);
    }
}
