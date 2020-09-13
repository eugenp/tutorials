package com.bealdung.hexagonal.controller;

import com.bealdung.hexagonal.domain.Dessert;
import com.bealdung.hexagonal.port.inbound.DessertService;
import com.bealdung.hexagonal.port.inbound.DessertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dessert")
public class DessertControllerImpl implements DessertController {

    @Autowired
    private DessertService dessertService;

    @Override
    public void createDessert(@RequestBody Dessert dessert)
    {
        dessertService.createDessert(dessert);
    }

    @Override
    public Dessert getDessert(String name) {
        return dessertService.getDessert(name);
    }

    @Override
    public List<Dessert> listDessert() {
        return dessertService.listDessert();
    }
}
