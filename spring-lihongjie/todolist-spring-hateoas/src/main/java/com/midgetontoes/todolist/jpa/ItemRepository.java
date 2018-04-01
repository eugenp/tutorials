package com.midgetontoes.todolist.jpa;

import com.midgetontoes.todolist.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Collection<Item> findByListId(Long id);
}
