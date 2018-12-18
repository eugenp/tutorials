package com.baeldung.hexagonal.persistence;

import com.baeldung.hexagonal.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemRepository {

    public void store(Item i){

    }

    public Item retrieve(String id){
        return new Item(id, "dummy item");
    }
}
