package com.baeldung.domain;

import com.baeldung.crosscutting.TodoItemNotFoundException;

import java.util.List;

public interface TodoItemService {

    public List<TodoItem> getItems();

    public TodoItem getItem(int id);

    public TodoItem addItem(TodoItem todoItem);

    public void updateItem(TodoItem todoItem) throws TodoItemNotFoundException;

    public TodoItem upgradePriority(TodoItem todoItem) throws TodoItemNotFoundException;

    public TodoItem degradePriority(TodoItem todoItem) throws TodoItemNotFoundException;



}
