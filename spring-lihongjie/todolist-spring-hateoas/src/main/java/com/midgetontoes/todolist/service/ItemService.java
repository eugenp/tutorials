package com.midgetontoes.todolist.service;

import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Collection<Item> findByListId(Long listId) {
        return itemRepository.findByListId(listId);
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public Item markAsCompleted(Long id) {
        Item item = itemRepository.findOne(id);
        if (item != null) {
            item.markAsCompleted();
            save(item);
        }
        return item;
    }

    public Item markAsUncompleted(Long id) {
        Item item = itemRepository.findOne(id);
        if (item != null) {
            item.markAsUncompleted();
            save(item);
        }
        return item;
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.delete(id);
    }
}
