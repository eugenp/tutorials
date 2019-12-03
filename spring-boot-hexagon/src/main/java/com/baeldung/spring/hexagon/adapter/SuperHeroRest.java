package com.baeldung.spring.hexagon.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.hexagon.domain.SuperHero;
import com.baeldung.spring.hexagon.port.Rest;
import com.baeldung.spring.hexagon.port.Service;

@RestController
public class SuperHeroRest implements Rest {
    @Autowired
    Service superHeroService;
    
    @GetMapping("/hero")
    public List<SuperHero> getAll() {
        // log.info("Get all super heros");
        return superHeroService.getAll();
    }
    
    @GetMapping("/hero/{id}")
    public SuperHero getSuperHero(@PathVariable("id") int id) {
        return superHeroService.getSuperHeroById(id);
    }
    
    @DeleteMapping("/heros/{id}")
    public void delete(@PathVariable("id") int id) {
        superHeroService.delete(id);
    }
    
    @PostMapping("/hero")
    public int save(@RequestBody SuperHero hero) {
        superHeroService.save(hero);
        return hero.getId();
    }
    
    @GetMapping("/hero/{id}/fight/{id2}")
    public SuperHero fight(@PathVariable("id") int id, @PathVariable("id2") int id2) {
        return superHeroService.fight(id, id2);
    }
}
