package com.baeldung.dockercompose.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.baeldung.dockercompose.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{name:'?0'}")
    Item findItemByName(String name);

    @Query(value = "{category:'?0'}")
    List<Item> findAllByCategory(String category);

    long count();
}

