package com.baeldung.application;

import com.baeldung.crosscutting.TodoItemNotFoundException;
import com.baeldung.domain.TodoItem;

import java.util.List;

public interface ConsoleAppService {

    public void showAllItems();

    public void showItem(int id);

    public TodoItem addItem(TodoItem todoItem);

    public TodoItem getItem(int id);

    public void updateItem(TodoItem todoItem) throws TodoItemNotFoundException;

    public TodoItem upgradePriority(TodoItem todoItem) throws TodoItemNotFoundException;

    public TodoItem degradePriority(TodoItem todoItem) throws TodoItemNotFoundException;
}
