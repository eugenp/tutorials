package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        SkillsMatcherTools skillsMatcherTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a skills-matching assistant for recruiters.
                Use the match-skills tool to compare a candidate's skills
                against a job's required skills, then summarize the result.
                """)
            .defaultTools(skillsMatcherTools)
            .build();
    }
}