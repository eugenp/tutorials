package com.baeldung.cats.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baeldung.cats.model.Item;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemService {
    List<Item> db = new ArrayList<>();

    public Item insert(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID()
                .toString());
        }
        db.add(item);
        return item;
    }

    public List<Item> findAll() {
        return db;
    }

    public List<Item> findAllByName(String name) {
        return db.stream()
            .filter(i -> i.getName()
                .equals(name))
            .toList();
    }

    public Item findById(String id) {
        return db.stream()
            .filter(i -> i.getId()
                .equals(id))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(String id) {
        Item item = findById(id);
        db.remove(item);
    }

    public List<Item> deleteAllByName(String name) {
        List<Item> allByName = findAllByName(name);
        db.removeAll(allByName);
        return allByName;
    }
}
