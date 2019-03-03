package com.baeldung.dao.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baeldung.domain.Item;

@Repository
public interface CustomItemRepository {

    void deleteCustom(Item entity);

    Item findItemById(Long id);

    void findThenDelete(Long id);

    List<Item> findItemsByColorAndGrade();

    List<Item> findItemByColorOrGrade();
}
