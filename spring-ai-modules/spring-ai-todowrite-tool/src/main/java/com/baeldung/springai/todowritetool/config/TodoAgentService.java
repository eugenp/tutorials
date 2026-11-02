package com.baeldung.springai.todowritetool.config;

import com.baeldung.springai.todowritetool.TodoItem;
import com.baeldung.springai.todowritetool.TodoService;
import com.baeldung.springai.todowritetool.TodoWriteTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoAgentService {

    private final ChatClient chatClient;

    public TodoAgentService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.clone()
            .defaultSystem("""
                You are a task-tracking assistant.
                When the user asks you to track, plan, or list steps, you MUST call the todoWrite tool
                with a complete todo list. Use string ids such as "1", "2", "3", statuses
                pending/in_progress/completed, and priorities high/medium/low.
                After the tool returns, briefly confirm what was recorded.
                """)
            .build();
    }

    public record AskResult(String response, List<TodoItem> todos) {}

    public AskResult ask(String userMessage) {
        TodoService todoService = new TodoService();
        TodoWriteTool tool = new TodoWriteTool(todoService);
        String content = chatClient.prompt()
            .user(userMessage)
            .tools(tool)
            .call()
            .content();
        return new AskResult(content, todoService.read());
    }
}
