package com.baeldung.hexagon.adaptor.primary;

import com.baeldung.hexagon.core.domain.Burger;
import com.baeldung.hexagon.port.inbound.BurgerService;
import com.baeldung.hexagon.ui.BurgerRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burger")
public class BurgerRestControllerImpl implements BurgerRestController {

    @Autowired
    private BurgerService burgerService;

    @Override
    public void createBurger(@RequestBody Burger burger) {
        burgerService.createBurger(burger);
    }

    @Override
    public Burger findByName(@PathVariable String name) {
        return burgerService.findByName(name);
    }

    @Override
    public List<Burger> findAll() {
        return burgerService.findAll();
    }
}
