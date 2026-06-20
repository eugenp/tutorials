package com.baeldung.springai.mcp.ui;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
class McpUiApplication {

    public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(McpUiApplication.class)
				.profiles("mcp-ui")
				.run(args);
    }

	@McpTool(
			title = "Say Hello",
			name = "say-hello",
			description = "A simple tool that returns a greeting message."
	)
	String sayHello() {
		return "Hello from the MCP UI Application!";
	}

	/*
	 	npx @modelcontextprotocol/inspector
	  	claude mcp add --transport http sport-spinner http://localhost:3001/mcp
 	*/
}
