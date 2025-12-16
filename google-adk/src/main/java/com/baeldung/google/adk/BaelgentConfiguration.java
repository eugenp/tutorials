package com.baeldung.google.adk;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import com.google.adk.tools.GoogleSearchTool;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@EnableConfigurationProperties(BaelgentProperties.class)
class BaelgentConfiguration {

    @Bean
    BaseAgent baseAgent(BaelgentProperties baelgentProperties) throws IOException {
        return LlmAgent
            .builder()
            .name(baelgentProperties.name())
            .description(baelgentProperties.description())
            .model(baelgentProperties.aiModel())
            .instruction(baelgentProperties.instruction().getContentAsString(Charset.defaultCharset()))
            .tools(
                FunctionTool.create(BaeldungAuthorFetcher.class, "fetch")
            )
            .build();
    }

}