package com.baeldung.agentskills;

import java.nio.file.Paths;

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
        String skillsRootDirectory = ".openai/skills";
        return ChatClient
            .builder(chatModel)
            .defaultTools(SkillsTool.builder()
                .addSkillsDirectory(skillsRootDirectory)
                .build())
            .defaultTools(FileSystemTools.builder()
                .allowedDirectory(Paths.get(skillsRootDirectory))
                .build())
            .defaultTools(ShellTools.builder().build())
            .build();
    }

}