package com.baeldung.architecture.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class ItemTest
{
    private final String NAME= "name";

    Item item = new Item(NAME);

    @Test
    void getId()
    {
        assertFalse(item.getId() == null);
    }

    @Test
    void getName()
    {
        assertEquals(item.getName(), NAME);
    }

    @Test
    void setName()
    {
        String otherName = "other name";
        Item otherItem = new Item(NAME);
        otherItem.setName(otherName);

        assertEquals(otherItem.getName(), otherName);
    }
}