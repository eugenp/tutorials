package com.baeldung.application;

import com.baeldung.crosscutting.TodoItemNotFoundException;
import com.baeldung.domain.TodoItem;
import com.baeldung.domain.TodoItemService;

import java.util.List;

public class ConsoleAppServiceImpl implements ConsoleAppService {

    private TodoItemService todoItemService;

    public ConsoleAppServiceImpl(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    public void showAllItems() {
        for (TodoItem todoItem : todoItemService.getItems()){
            System.out.println(todoItem);
        }
    }

    public void showItem(int id) {
        System.out.println(todoItemService.getItem(id));
    }

    public TodoItem addItem(TodoItem todoItem) {
        return todoItemService.addItem(todoItem);
    }

    @Override
    public TodoItem getItem(int id) {
        return todoItemService.getItem(id);
    }

    public void updateItem(TodoItem todoItem) throws TodoItemNotFoundException {
        todoItemService.updateItem(todoItem);
    }

    @Override
    public TodoItem upgradePriority(TodoItem todoItem) throws TodoItemNotFoundException {
        return todoItemService.upgradePriority(todoItem);
    }

    @Override
    public TodoItem degradePriority(TodoItem todoItem) throws TodoItemNotFoundException {
        return todoItemService.degradePriority(todoItem);
    }

}
