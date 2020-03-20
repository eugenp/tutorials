package com.bealdung.hexagonalarchitecture.web;

import com.bealdung.hexagonalarchitecture.core.Food;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FoodRestUI {

    @PostMapping
    public ResponseEntity createFood(@RequestBody Food food);

    @GetMapping("/{name}")
    public ResponseEntity<Food> getFood(@PathVariable String name);

    @GetMapping
    public ResponseEntity<List<Food>> listFood();
}
