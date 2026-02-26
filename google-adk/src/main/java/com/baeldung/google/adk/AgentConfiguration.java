package com.baeldung.google.adk;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@EnableConfigurationProperties(AgentProperties.class)
class AgentConfiguration {

    @Bean
    BaseAgent baseAgent(AgentProperties agentProperties) throws IOException {
        return LlmAgent
            .builder()
            .name(agentProperties.name())
            .description(agentProperties.description())
            .model(agentProperties.aiModel())
            .instruction(agentProperties.systemPrompt().getContentAsString(Charset.defaultCharset()))
            .tools(
                FunctionTool.create(AuthorFetcher.class, "fetch")
            )
            .build();
    }
}