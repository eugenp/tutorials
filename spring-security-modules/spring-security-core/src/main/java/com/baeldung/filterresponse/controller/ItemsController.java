package com.baeldung.filterresponse.controller;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.filterresponse.model.Item;

@RestController
public class ItemsController {

    @RequestMapping("/items")
    public Collection<Item> getItems() {
        return Arrays.asList(new Item(1, "Item 1", "Frank"), new Item(2, "Item 2", "Bob"));
    }

}
