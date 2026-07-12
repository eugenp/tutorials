package com.baeldung.springai.subagentorchestrator;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springaicommunity.agent.common.task.subagent.SubagentType;
import org.springaicommunity.agent.tools.task.TaskTool;
import org.springaicommunity.agent.tools.task.claude.ClaudeSubagentReferences;
import org.springaicommunity.agent.tools.task.claude.ClaudeSubagentType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class SubagentOrchestrationIntegrationTest {

    @Value("${agent.tasks.paths}")
    private List<Resource> agentPaths;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void givenSubagentDefinitions_whenLoadingSubagents_thenReferencesAreCreated() {

        var references = ClaudeSubagentReferences
            .fromResources(agentPaths);

        assertThat(references).isNotNull();
    }

    @Test
    void givenPrompt_whenExecutingOrchestration_thenResponseIsGenerated() {
        List<Resource> agentResources = List.of(
            new ClassPathResource("agents/test-agent.md"));

        SubagentType claudeType = ClaudeSubagentType.builder()
            .chatClientBuilder("default", chatClientBuilder.clone())
            .build();

        ToolCallback taskTool = TaskTool.builder()
            .subagentReferences(
                ClaudeSubagentReferences.fromResources(agentResources))
            .subagentTypes(claudeType)
            .build();

        ChatClient chatClient = chatClientBuilder.clone()
            .defaultToolCallbacks(taskTool)
            .build();

        String result = chatClient
            .prompt("Explain how the authentication module works.")
            .call()
            .content();

        assertThat(result).isNotBlank();
        assertThat(result).contains("authentication");
    }
}
