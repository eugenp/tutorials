package com.baeldung.groq;

import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomGroqChatService {
    @Autowired
    private OpenAiChatModel customGroqChatClient;

    public String chat(String prompt, String model, Double temperature) {
        ChatOptions chatOptions = OpenAiChatOptions.builder()
            .model(model)
            .temperature(temperature)
            .build();
        return customGroqChatClient.call(new Prompt(prompt, chatOptions))
            .getResult()
            .getOutput()
            .getText();
    }
}
