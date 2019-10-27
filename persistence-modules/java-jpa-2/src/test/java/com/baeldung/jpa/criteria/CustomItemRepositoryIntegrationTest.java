package com.baeldung.jpa.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.baeldung.jpa.criteria.Item;
import com.baeldung.jpa.criteria.CustomItemRepository;

public class CustomItemRepositoryIntegrationTest {

    CustomItemRepository customItemRepository = new CustomItemRepositoryImpl();

    @Test
    public void givenItems_whenFindItemsByColorAndGrade_thenReturnItems() {

        List<Item> items = customItemRepository.findItemsByColorAndGrade();

        assertFalse("No items found", items.isEmpty());
        assertEquals("There should be only one item", 1, items.size());

        Item item = items.get(0);

        assertEquals("this item do not have blue color", "blue", item.getColor());
        assertEquals("this item does not belong to A grade", "A", item.getGrade());
    }

    @Test
    public void givenItems_whenFindItemByColorOrGrade_thenReturnItems() {

        List<Item> items = customItemRepository.findItemByColorOrGrade();

        assertFalse("No items found", items.isEmpty());
        assertEquals("There should be only one item", 1, items.size());

        Item item = items.get(0);

        assertEquals("this item do not have red color", "red", item.getColor());
        assertEquals("this item does not belong to D grade", "D", item.getGrade());
    }
}
