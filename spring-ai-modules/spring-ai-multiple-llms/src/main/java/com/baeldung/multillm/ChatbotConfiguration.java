package com.baeldung.multillm;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class ChatbotConfiguration {

    @Bean
    @Primary
    ChatClient primaryChatClient(OpenAiChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

    @Bean
    ChatClient secondaryChatClient(AnthropicChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

    @Bean
    ChatModel tertiaryChatModel(
        AnthropicApi anthropicApi,
        AnthropicChatModel anthropicChatModel,
        @Value("${spring.ai.anthropic.chat.options.tertiary-model}") String tertiaryModelName
    ) {
        AnthropicChatOptions chatOptions = anthropicChatModel.getDefaultOptions().copy();
        chatOptions.setModel(tertiaryModelName);
        return AnthropicChatModel.builder()
            .anthropicApi(anthropicApi)
            .defaultOptions(chatOptions)
            .build();
    }

    @Bean
    ChatClient tertiaryChatClient(@Qualifier("tertiaryChatModel") ChatModel tertiaryChatModel) {
        return ChatClient.create(tertiaryChatModel);
    }

}