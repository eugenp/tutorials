package com.baeldung.hexagonal.api;

import com.baeldung.hexagonal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController(value="/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(path="/{id}")
    public String borrowItem(@PathVariable String id) {
        if(itemService.borrow(id, "anonymous")) {
            return "ok";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }
}
