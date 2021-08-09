package com.baeldung.hexagon.ui;

import com.baeldung.hexagon.core.domain.Burger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BurgerRestController {

    @PostMapping
    void createBurger(@RequestBody Burger burger);

    @GetMapping("/{name}")
    Burger findByName(@PathVariable String name);

    @GetMapping
    List<Burger> findAll();
}
