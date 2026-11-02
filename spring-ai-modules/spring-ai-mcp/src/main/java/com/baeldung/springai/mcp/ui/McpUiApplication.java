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
	    mvn -q -DskipTests package
	    npx @modelcontextprotocol/inspector java -jar target/spring-ai-mcp-0.0.1.jar

  		claude mcp add-json sport-spinner '{"command":"java","args":["-jar","D:/repos/tutorials/spring-ai-modules/spring-ai-mcp/target/spring-ai-mcp-0.0.1.jar"]}'
	*/
}
