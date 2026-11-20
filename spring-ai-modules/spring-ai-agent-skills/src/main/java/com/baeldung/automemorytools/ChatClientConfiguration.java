package com.baeldung.automemorytools;

import org.springaicommunity.agent.advisors.AutoMemoryToolsAdvisor;
import org.springaicommunity.agent.tools.AutoMemoryTools;
import org.springaicommunity.agent.tools.FileSystemTools;
import org.springaicommunity.agent.tools.ShellTools;
import org.springaicommunity.agent.tools.TodoWriteTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

@Configuration
class ChatClientConfiguration {

    // Option A, Option B, Option C
    @Value("${agent.memory.dir}")
    String memoryDirectory;

    // Option B
    @Value("classpath:/prompts/AUTO_MEMORY_TOOLS_SYSTEM_PROMPT.md")
    Resource memorySystemPromptAutoMemoryTools;

    // Option C
    @Value("classpath:/prompt/AUTO_MEMORY_FILESYSTEM_TOOLS_SYSTEM_PROMPT.md")
    Resource memorySystemPromptFilesystemTools;

    // Option A
    @Bean
    @Primary
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .defaultAdvisors(
                AutoMemoryToolsAdvisor.builder()
                    .memoriesRootDirectory(memoryDirectory)
                    .build(),
                MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder()
                        .maxMessages(100)
                        .build())
                    .build(),
                ToolCallingAdvisor.builder()
                    .disableInternalConversationHistory()
                    .build())
            .build();
    }

    // Option B
    @Bean
    ChatClient chatClientWithMoreSystemPrompt(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .defaultSystem(p -> p
                .text(memorySystemPromptAutoMemoryTools)
                .param("MEMORIES_ROOT_DIERCTORY", memoryDirectory))
            .defaultTools(
                AutoMemoryTools.builder()
                    .memoriesDir(memoryDirectory)
                    .build(),
                TodoWriteTool.builder()
                    .build())
            .defaultAdvisors(ToolCallingAdvisor.builder()
                .build())
            .build();
    }

    // Option C
    @Bean
    ChatClient chatClientWithoutAutoMemoryTools(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .defaultSystem(p -> p
                .text(memorySystemPromptFilesystemTools)
                .param("MEMORIES_ROOT_DIERCTORY", memoryDirectory))   // tells the agent where to write
            .defaultTools(
                ShellTools.builder()
                    .build(),         // Bash — mkdir, ls, etc.
                FileSystemTools.builder()
                    .build())    // Read, Write, Edit — memory file operations
            .defaultAdvisors(ToolCallingAdvisor.builder()
                .build())
            .build();
    }
}
