package com.baeldung.domain;

import com.baeldung.crosscutting.TodoItemNotFoundException;

import java.util.List;
import java.util.ListIterator;

public class TodoItemServiceImpl implements TodoItemService {
    private TodoItemRepository todoItemRepository;

    public TodoItemServiceImpl(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItem> getItems() {
        return todoItemRepository.getItems();
    }

    @Override
    public TodoItem getItem(int id) {
        return todoItemRepository.getItem(id);
    }

    @Override
    public TodoItem addItem(TodoItem todoItem) {
        return todoItemRepository.addItem(todoItem);
    }

    @Override
    public void updateItem(TodoItem todoItem) throws TodoItemNotFoundException {
        todoItemRepository.updateItem(todoItem);
    }

    @Override
    public TodoItem upgradePriority(TodoItem todoItem) {
        TodoItem item = todoItemRepository.getItem(todoItem.getId());
        if (item == null){
            throw new TodoItemNotFoundException("Item not found");
        }
        if ( item.getItemPriority() == ItemPriority.HIGH){
            item.setItemPriority(ItemPriority.HIGH);
        } else if ( item.getItemPriority() == ItemPriority.MED){
            item.setItemPriority(ItemPriority.HIGH);
        } else {
            item.setItemPriority(ItemPriority.MED);
        }

        return item;
    }

    @Override
    public TodoItem degradePriority(TodoItem todoItem) {
        TodoItem item = todoItemRepository.getItem(todoItem.getId());
        if (item == null){
            throw new TodoItemNotFoundException("Item not found");
        }
        if ( item.getItemPriority() == ItemPriority.HIGH){
            item.setItemPriority(ItemPriority.MED);
        } else if( item.getItemPriority() == ItemPriority.MED){
            item.setItemPriority(ItemPriority.LOW);
        } else {
            item.setItemPriority(ItemPriority.LOW);
        }

        return item;
    }
}
