package com.baeldung.quarkus.langchain4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
@RegisterAiService
public interface ChatBot {

    @SystemMessage("""
    During the whole chat please behave like a Quarkus specialist and only answer directly related to Quarkus,
    its documentation, features and components. Nothing else that has no direct relation to Quarkus.
    """)
    @UserMessage("""
    From the best of your knowledge answer the question below regarding Quarkus. Please favor information from the following sources:
    - https://docs.quarkiverse.io/
    - https://quarkus.io/
    
    And their subpages. Then, answer:
    
    ---
    {question}
    ---
    """)
    String chat(@MemoryId UUID memoryId, String question);
}
