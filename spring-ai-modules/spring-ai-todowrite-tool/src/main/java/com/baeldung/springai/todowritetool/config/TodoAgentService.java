package com.baeldung.springai.todowritetool.config;

import com.baeldung.springai.todowritetool.TodoWriteTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TodoAgentService {

    private final ChatClient chatClient;

    public TodoAgentService(ChatClient.Builder chatClientBuilder, TodoWriteTool todoWriteTool) {
        this.chatClient = chatClientBuilder.clone()
            .defaultSystem("""
                You are a task-tracking assistant.
                When the user asks you to track, plan, or list steps, you MUST call the todoWrite tool
                with a complete todo list. Use string ids such as "1", "2", "3", statuses
                pending/in_progress/completed, and priorities high/medium/low.
                After the tool returns, briefly confirm what was recorded.
                """)
            .defaultTools(todoWriteTool)
            .build();
    }

    public String ask(String userMessage) {
        return chatClient.prompt()
            .user(userMessage)
            .call()
            .content();
    }
}
