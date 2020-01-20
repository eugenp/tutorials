package com.baeldung.architecture.hexagonal.usecase;

import java.util.*;

import com.baeldung.architecture.hexagonal.domain.*;
import com.baeldung.architecture.hexagonal.port.input.*;
import com.baeldung.architecture.hexagonal.port.output.*;

public class CreateItemUseCase implements CreateItem
{
    private ItemRepository itemRepository;

    public CreateItemUseCase(ItemRepository itemRepository)
    {
        this.itemRepository = itemRepository;
    }

    public UUID createItem(String name)
    {
        Item item = new Item(name);
        itemRepository.persistItem(item);
        return item.getId();
    }
}
