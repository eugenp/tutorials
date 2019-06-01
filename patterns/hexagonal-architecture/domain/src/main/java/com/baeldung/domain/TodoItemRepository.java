package com.baeldung.domain;


import com.baeldung.crosscutting.TodoItemNotFoundException;

import java.util.List;

public interface TodoItemRepository {

    public List<TodoItem> getItems();

    public TodoItem getItem(int id);

    public TodoItem addItem(TodoItem todoItem);

    public void updateItem(TodoItem todoItem) throws TodoItemNotFoundException;

    public void removeItem(int id) throws TodoItemNotFoundException;

}
