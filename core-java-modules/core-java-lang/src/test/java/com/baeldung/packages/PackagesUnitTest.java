package com.baeldung.packages;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.baeldung.packages.domain.TodoItem;

public class PackagesUnitTest {

    @Test
    public void whenTodoItemAdded_ThenSizeIncreases() {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setDescription("Test the Todo List");
        todoItem.setDueDate(LocalDate.now());
        TodoList todoList = new TodoList();
        todoList.addTodoItem(todoItem);
        assertEquals(1, todoList.getTodoItems().size());
    }
}
