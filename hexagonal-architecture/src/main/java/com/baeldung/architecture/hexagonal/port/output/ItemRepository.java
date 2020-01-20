package com.baeldung.architecture.hexagonal.port.output;

import java.util.*;

import com.baeldung.architecture.hexagonal.domain.*;

public interface ItemRepository
{
    void persistItem(Item item);
    Collection<Item> getItems();
}
