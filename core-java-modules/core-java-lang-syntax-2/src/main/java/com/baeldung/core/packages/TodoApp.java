package com.baeldung.core.packages;

import com.baeldung.core.packages.domain.TodoItem;

import java.time.LocalDate;

public class TodoApp {

    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        for (int i = 0; i < 3; i++) {
            TodoItem item = new TodoItem();
            item.setId(Long.valueOf((long)i));
            item.setDescription("Todo item " + (i + 1));
            item.setDueDate(LocalDate.now().plusDays((long)(i + 1)));
            todoList.addTodoItem(item);
        }

        todoList.getTodoItems().forEach((TodoItem todoItem) -> System.out.println(todoItem.toString()));
    }

}
