package com.baeldung.springai.todowritetool;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TodoWriteTool {

    private final TodoService todoService;

    public TodoWriteTool(TodoService todoService) {
        this.todoService = todoService;
    }

    @Tool(description = "Create or update the structured todo list for the "
        + "current session, replacing any previous list")
    public List<TodoItem> todoWrite(
        @ToolParam(description = "The full list of todo items, including "
            + "unchanged ones") List<TodoItem> todos) {
        return todoService.write(todos);
    }

    @Tool(description = "Read the current todo list for the session")
    public List<TodoItem> todoRead() {
        return todoService.read();
    }
}
