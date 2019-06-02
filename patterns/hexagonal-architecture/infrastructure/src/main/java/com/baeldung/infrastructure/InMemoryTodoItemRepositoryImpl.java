package com.baeldung.infrastructure;

import com.baeldung.domain.TodoItem;
import com.baeldung.domain.ItemPriority;
import com.baeldung.domain.TodoItemRepository;
import com.baeldung.crosscutting.TodoItemNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;

public class InMemoryTodoItemRepositoryImpl implements TodoItemRepository {

    private static int todoItemId = 1;
    private final List<TodoItem> items = new ArrayList<TodoItem>();


    public TodoItemRepositoryImpl() {
        // add some dummy data
        addItem(new TodoItem("first", "first details", ItemPriority.HIGH));
        addItem(new TodoItem("second", "second details", ItemPriority.MED));
        addItem(new TodoItem("third", "third details", ItemPriority.LOW));
    }

    public List<TodoItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public TodoItem getItem(int id) {
        for(TodoItem item : items) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public TodoItem addItem(TodoItem todoItem) {
        todoItem.setId(todoItemId);
        items.add(todoItem);
        todoItemId++;
        return todoItem;
    }

    public void updateItem(TodoItem todoItem) {
        ListIterator<TodoItem> itemIterator = items.listIterator();
        boolean found = false;

        while(itemIterator.hasNext()) {
            TodoItem item = itemIterator.next();

            if(todoItem.equals(item)) {
                itemIterator.set(todoItem);
                found = true;
                break;
            }
        }
        if (found == false) {
            throw new TodoItemNotFoundException(todoItem + " not found");
        }
    }

    public void removeItem(int id) {
        ListIterator<TodoItem> itemIterator = items.listIterator();
        boolean found = false;

        while(itemIterator.hasNext()) {
            TodoItem item = itemIterator.next();

            if(item.getId() == id) {
                itemIterator.remove();
                found = true;
                break;
            }
        }
        if (found == false) {
            throw new TodoItemNotFoundException("Todo Item with id " + id + " not found");
        }
    }
}
