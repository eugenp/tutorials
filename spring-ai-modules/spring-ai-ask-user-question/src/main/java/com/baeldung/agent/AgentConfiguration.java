package com.baeldung.agent;

import org.springaicommunity.agent.tools.AskUserQuestionTool;
import org.springaicommunity.agent.utils.CommandLineQuestionHandler;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AgentConfiguration {

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
            .maxMessages(20)
            .build();
        
        return ChatClient.builder(chatModel)
            .defaultSystem("You are an interactive CLI assistant.\n"
                + "Be helpful, concise.\n"
                + "If you need information, a preference or disambiguation from the user,\n"
                + "Always use an available tool to ask - never ask in ordinary assistant text.\n"
                + "After receiving the tool result, continue with the response.\n")
            .defaultTools(
                AskUserQuestionTool.builder()
                    .questionHandler(new CommandLineQuestionHandler())
                    .build()
            )
            .defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build()
            )
            .build();
    }
}