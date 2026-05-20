package com.baeldung.agentskills;

import org.springaicommunity.agent.tools.FileSystemTools;
import org.springaicommunity.agent.tools.ShellTools;
import org.springaicommunity.agent.tools.SkillsTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatbotConfiguration {

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .defaultToolCallbacks(SkillsTool.builder()
                .addSkillsDirectory(".openai/skills")
                .build())
            .defaultTools(FileSystemTools.builder().build())
            .defaultTools(ShellTools.builder().build())
            .build();
    }

}