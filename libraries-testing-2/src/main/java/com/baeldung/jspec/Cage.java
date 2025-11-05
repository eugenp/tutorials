package com.baeldung.jspec;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cage {
    
    private Set<Animal> animals = new HashSet<>();
    
    public void put(Animal animal) {
        animals.add(animal);
    }
    
    public void put(Animal... animals) {
        this.animals.addAll(Arrays.asList(animals));
    }
    
    public Animal release(Animal animal) {
        return animals.remove(animal) ? animal : null;
    }
    
    public void open() {
        animals.clear();
    }
    
    public boolean hasAnimals() {
        return animals.size() > 0;
    }
    
    public boolean isEmpty() {
        return animals.isEmpty();
    }
    
    public Set<Animal> getAnimals() {
        return this.animals;
    }
    
    public int size() {
        return animals.size();
    }

    @Override
    public String toString() {
        return "Cage [animals=" + animals + "]";
    }
    
}
