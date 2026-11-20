package com.baeldung.springai.subagentorchestrator.config;

import java.util.List;

import org.springaicommunity.agent.common.task.subagent.SubagentType;
import org.springaicommunity.agent.tools.task.TaskTool;
import org.springaicommunity.agent.tools.task.claude.ClaudeSubagentReferences;
import org.springaicommunity.agent.tools.task.claude.ClaudeSubagentType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

@Configuration
public class AgentConfig {

    @Value("${agent.tasks.paths}")
    private List<Resource> agentPaths;

    @Bean
    @Primary
    public ChatClient orchestratorChatClient(ChatClient.Builder chatClientBuilder) {

        SubagentType claudeType = ClaudeSubagentType.builder()
            .chatClientBuilder("default", chatClientBuilder.clone())
            .build();

        ToolCallback taskTool = TaskTool.builder()
            .subagentReferences(
                ClaudeSubagentReferences.fromResources(agentPaths))
            .subagentTypes(claudeType)
            .build();

        return chatClientBuilder.clone()
            .defaultToolCallbacks(taskTool)
            .build();
    }
}
