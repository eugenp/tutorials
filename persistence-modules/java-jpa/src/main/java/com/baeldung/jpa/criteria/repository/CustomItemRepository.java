package com.baeldung.jpa.criteria.repository;

import java.util.List;

import com.baeldung.jpa.criteria.entity.Item;

public interface CustomItemRepository {

    List<Item> findItemsByColorAndGrade();

    List<Item> findItemByColorOrGrade();
}
