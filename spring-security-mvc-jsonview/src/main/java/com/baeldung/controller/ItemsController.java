package com.baeldung.controller;

import com.baeldung.model.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RestController
public class ItemsController {

    @RequestMapping("/items")
    public Collection<Item> getItems() {
        return Arrays.asList(new Item(1, "Item 1", "Frank"), new Item(2, "Item 2", "Bob"));
    }

}
