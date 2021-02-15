package com.baeldung.hexagonal.architecture.controller;

import com.baeldung.hexagonal.architecture.model.Pizza;
import com.baeldung.hexagonal.architecture.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @PostMapping(value ="/create")
    public void createPizza(@RequestParam("name") String name) {
        pizzaService.createPizza(name);
    }

    @GetMapping("/get")
    public Pizza getPizza(@RequestParam("name") String name) {
        return pizzaService.getPizza(name);
    }

    @GetMapping("/list")
    public List<Pizza> listPizza() {
        return pizzaService.listPizza();
    }
}