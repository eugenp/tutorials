package com.hexarch.jpa;

import com.hexarch.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
