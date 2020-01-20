package com.baeldung.architecture.hexagonal.domain;

import java.util.*;

public class Item
{
    private UUID id;
    private String name;


    public Item(String name)
    {
        id = UUID.randomUUID();
        this.setName(name);
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
