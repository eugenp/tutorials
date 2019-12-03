package com.baeldung.spring.hexagon.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.baeldung.spring.hexagon.domain.SuperHero;
import com.baeldung.spring.hexagon.domain.Type;
import com.baeldung.spring.hexagon.port.Repository;
import com.baeldung.spring.hexagon.port.Service;

@Component
public class SuperHeroService implements Service {

    @Autowired
    Repository superHeroRepository;
    
    RestTemplate restTemplate = new RestTemplate();

    public List<SuperHero> getAll() {
        List<SuperHero> heros = new ArrayList<SuperHero>();
        superHeroRepository.findAll().forEach(hero -> heros.add(hero));
        return heros;
    }

    public SuperHero getSuperHeroById(int id) {
        return superHeroRepository.findById(id).get();
    }

    public void save(SuperHero hero) {
        superHeroRepository.save(hero);
    }

    public SuperHero fight(int id1, int id2) {
        SuperHero hero1 = getSuperHeroById(id1);
        SuperHero hero2 = getSuperHeroById(id2);
        
        SuperHero winner = null;
        if (hero1.getPower() > hero2.getPower())
            winner = hero1;
        else if (hero2.getPower() > hero1.getPower())
            winner = hero2;
        else {
            if (hero1.getType() == Type.MARVEL) {
                if (hero2.getType() == Type.DC)
                    winner = hero1;
                else 
                    winner = new SuperHero();
            }
            else {
                if (hero2.getType() == Type.MARVEL)
                    winner = hero2;
                else
                    winner = new SuperHero();
            }
        }
        return winner;
    }

    public void delete(int id) {
        superHeroRepository.deleteById(id);
    }
  
    public void setRestTemplate(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
    }
}
