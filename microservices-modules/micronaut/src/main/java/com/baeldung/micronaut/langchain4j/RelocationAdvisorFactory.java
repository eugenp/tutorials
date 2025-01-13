package com.baeldung.micronaut.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Factory
public class RelocationAdvisorFactory {

    @Value("${langchain4j.open-ai.api-key}")
    private String apiKey;

    @Singleton
    public RelocationAdvisor advisor() {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .build();

        return AiServices.create(RelocationAdvisor.class, model);
    }
}
