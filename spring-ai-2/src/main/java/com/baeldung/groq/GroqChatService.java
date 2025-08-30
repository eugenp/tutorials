package com.baeldung.groq;

import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroqChatService {
    @Autowired
    private OpenAiChatModel groqClient;

    public String chat(String prompt) {

        return groqClient.call(prompt);
    }

    public ChatOptions getChatOptions() {
        return groqClient.getDefaultOptions();
    }
}
