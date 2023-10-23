package com.baeldung.langchain;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class ServiceWithToolsLiveTest {

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
            .chatLanguageModel(OpenAiChatModel.withApiKey(Constants.OPEN_API_KEY))
            .tools(new Calculator())
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build();

        String question = "What is the sum of the numbers of letters in the words \"language\" and \"model\"?";
        String answer = assistant.chat(question);

        Logger.getGlobal()
            .info(answer);
        Assert.assertNotNull(answer);

    }

}
