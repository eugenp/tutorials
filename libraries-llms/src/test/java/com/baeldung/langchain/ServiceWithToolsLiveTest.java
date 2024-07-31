package com.baeldung.langchain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class ServiceWithToolsLiveTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceWithToolsLiveTest.class);

    static class Calculator {
        @Tool("Calculates the length of a string")
        int stringLength(String s) {
            return s.length();
        }

        @Tool("Calculates the sum of two numbers")
        int add(int a, int b) {
            return a + b;
        }
    }

    interface Assistant {
        String chat(String userMessage);
    }

    @Test
    public void givenServiceWithTools_whenPrompted_thenValidResponse() {
        Assistant assistant = AiServices.builder(Assistant.class)
            .chatLanguageModel(OpenAiChatModel.withApiKey(Constants.OPENAI_API_KEY))
            .tools(new Calculator())
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build();

        String question = "What is the sum of the numbers of letters in the words \"language\" and \"model\"?";
        String answer = assistant.chat(question);

        logger.info(answer);
        assertThat(answer).contains("13");
    }

}
