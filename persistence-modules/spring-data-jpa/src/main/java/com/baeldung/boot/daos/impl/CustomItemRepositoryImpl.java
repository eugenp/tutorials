package com.baeldung.boot.daos.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.boot.daos.CustomItemRepository;
import com.baeldung.boot.domain.Item;

@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void deleteCustom(Item item) {
        entityManager.remove(item);
    }

    @Override
    public Item findItemById(Long id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public void findThenDelete(Long id) {
        final Item item = entityManager.find(Item.class, id);
        entityManager.remove(item);
    }

}
