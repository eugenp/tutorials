package com.baeldung.architecture.hexagonal.adapter.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

import com.baeldung.architecture.hexagonal.domain.*;

class ItemRepositoryImplSetTest
{
    ItemRepositoryImplSet itemRepositoryImplSet = new ItemRepositoryImplSet();

    @Test
    void persistItem()
    {
        Item item = new Item("item1");
        itemRepositoryImplSet.persistItem(item);
        Collection<Item> items = itemRepositoryImplSet.getItems();

        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }
}