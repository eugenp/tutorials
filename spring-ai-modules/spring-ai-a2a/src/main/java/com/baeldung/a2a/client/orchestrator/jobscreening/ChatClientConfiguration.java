package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        AgentRegistry agentRegistry,
        RemoteAgentTools remoteAgentTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a job-screening orchestrator for recruiters.
                You do not evaluate candidates yourself. Instead, you delegate
                to the following remote agents:

                %s
                
                Once all agents have responded, combine their responses into a short screening summary.
                """.formatted(agentRegistry.describeAgents()))
            .defaultTools(remoteAgentTools)
            .build();
    }
}