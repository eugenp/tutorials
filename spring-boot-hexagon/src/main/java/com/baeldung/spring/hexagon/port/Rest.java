package com.baeldung.spring.hexagon.port;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.spring.hexagon.domain.SuperHero;

public interface Rest {
    @GetMapping("/hero") List<SuperHero> getAll();
    
    @GetMapping("/hero/{id}") SuperHero getSuperHero(@PathVariable("id") int id);
    
    @DeleteMapping("/heros/{id}") void delete(@PathVariable("id") int id);
    
    @PostMapping("/hero") int save(@RequestBody SuperHero hero);
    
    @GetMapping("/hero/{id}/fight/{id2}") 
        SuperHero fight(@PathVariable("id") int id, @PathVariable("id2") int id2);
}
