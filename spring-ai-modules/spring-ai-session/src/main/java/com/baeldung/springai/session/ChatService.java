package com.baeldung.springai.session;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.session.advisor.SessionMemoryAdvisor;
import org.springframework.stereotype.Component;

@Component
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatModel chatModel, SessionMemoryAdvisor sessionMemoryAdvisor) {
        this.chatClient = ChatClient.builder(chatModel)
            .defaultAdvisors(sessionMemoryAdvisor)
            .build();
    }

    public String chat(String sessionId, String prompt) {
        return chatClient.prompt()
            .user(prompt)
            .advisors(a -> a.param(SessionMemoryAdvisor.SESSION_ID_CONTEXT_KEY, sessionId))
            .call()
            .content();
    }

}
