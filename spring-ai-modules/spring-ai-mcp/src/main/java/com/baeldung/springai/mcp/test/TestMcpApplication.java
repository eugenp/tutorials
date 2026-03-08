package com.baeldung.springai.mcp.test;

import org.springframework.ai.model.anthropic.autoconfigure.AnthropicChatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Excluding the below auto-configuration to avoid start up
 * failure. Its corresponding starter is present on the classpath but is
 * only needed by the MCP client application.
 */
@SpringBootApplication(exclude = {
    AnthropicChatAutoConfiguration.class
})
@PropertySource("classpath:application-test-mcp-server.properties")
public class TestMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMcpApplication.class, args);
    }

}
