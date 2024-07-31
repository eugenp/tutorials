package com.baeldung.dockercompose.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dockercompose.model.Item;
import com.baeldung.dockercompose.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> save(final @RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Item findByName(@RequestParam final String name) {
        return itemRepository.findItemByName(name);
    }

    @GetMapping(value = "category", produces = APPLICATION_JSON_VALUE)
    public List<Item> findByCategory(@RequestParam final String category) {
        return itemRepository.findAllByCategory(category);
    }

    @GetMapping(value = "count", produces = APPLICATION_JSON_VALUE)
    public long count() {
        return itemRepository.count();
    }
}
