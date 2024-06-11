package com.baeldung.caching.redis;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable String id) {
        return itemService.getItemForId(id);
    }

}