package com.baeldung.architecture.hexagonal.adapter.persistence;

import java.util.*;

import com.baeldung.architecture.hexagonal.domain.*;
import com.baeldung.architecture.hexagonal.port.output.*;

public class ItemRepositoryImplSet implements ItemRepository
{
    private Set<Item> itemStorage = new HashSet<>();

    @Override
    public void persistItem(Item item)
    {
        itemStorage.add(item);
    }

    @Override
    public Collection<Item> getItems()
    {
        return itemStorage;
    }
}
