package com.baeldung.quizzard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        // MCP tools (fetch) come from the Docker MCP gateway, configured in application-docker-desktop.yaml
        app.setAdditionalProfiles("docker-desktop");
        app.run(args);
    }

}
