package com.baeldung.jpa.criteria;

import java.util.List;

public interface CustomItemRepository {

    List<Item> findItemsByColorAndGrade();

    List<Item> findItemByColorOrGrade();
}
