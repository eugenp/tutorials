package com.baeldung.quizzard;

import com.embabel.agent.config.annotation.EnableAgentShell;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.McpServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAgentShell
@SpringBootApplication
@EnableAgents(mcpServers = {
	McpServers.DOCKER_DESKTOP
})
class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}