package com.baeldung.pattern.simplehexagonalexample.controller;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;
import com.baeldung.pattern.simplehexagonalexample.domain.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/furniture")
public class FurnitureController {

    @Autowired
    private FurnitureService furnitureService;

    @PostMapping
    public Furniture manufactureFurniture(@RequestBody Furniture furniture) {
        return furnitureService.manufactureFurniture(furniture);
    }

    @GetMapping(path = "/{name}")
    public Furniture getFurniture(@PathVariable String name) {
        return furnitureService.getFurniture(name);
    }

    @GetMapping
    public List<Furniture> listAllFurniture() {
        return furnitureService.listAllFurniture();
    }
}

