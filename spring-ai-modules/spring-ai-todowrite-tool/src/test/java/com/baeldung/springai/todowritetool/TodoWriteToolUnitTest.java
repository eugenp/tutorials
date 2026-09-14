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
            new TodoItem("1", "Set up project", TodoItem.Status.completed, TodoItem.Priority.high),
            new TodoItem("2", "Write TodoWriteTool", TodoItem.Status.in_progress, TodoItem.Priority.high),
            new TodoItem("3", "Add tests", TodoItem.Status.pending, TodoItem.Priority.medium));

        List<TodoItem> written = todoWriteTool.todoWrite(todos);

        assertThat(written).hasSize(3);
        assertThat(todoWriteTool.todoRead())
            .extracting(TodoItem::status)
            .containsExactly(TodoItem.Status.completed, TodoItem.Status.in_progress, TodoItem.Status.pending);
    }
}
