package com.baeldung.springai.todowritetool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class TodoWriteToolUnitTest {

    @Test
    void whenTodoWriteIsCalled_thenTodoReadReturnsSameList() {
        TodoService todoService = new TodoService();
        TodoWriteTool todoWriteTool = new TodoWriteTool(todoService);
        List<TodoItem> todos = List.of(
            new TodoItem("1", "Set up project", "completed", "high"),
            new TodoItem("2", "Write TodoWriteTool", "in_progress", "high"),
            new TodoItem("3", "Add tests", "pending", "medium"));

        List<TodoItem> written = todoWriteTool.todoWrite(todos);

        assertThat(written).hasSize(3);
        assertThat(todoWriteTool.todoRead())
            .extracting(TodoItem::status)
            .containsExactly("completed", "in_progress", "pending");
    }
}
