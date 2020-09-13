package com.bealdung.hexagonal.controller;

import com.bealdung.hexagonal.domain.Dessert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DessertController{
    @PostMapping
    void createDessert(@RequestBody Dessert dessert);

    @GetMapping("/{name}")
    public Dessert getDessert(@PathVariable String name);

    @GetMapping
    public List<Dessert> listDessert();
}
