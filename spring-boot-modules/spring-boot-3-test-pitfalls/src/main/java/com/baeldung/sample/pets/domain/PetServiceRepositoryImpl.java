package com.baeldung.sample.pets.domain;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class PetServiceRepositoryImpl implements PetServiceRepository {

    private final Set<Pet> pets = new HashSet<>();

    @Override
    public Set<Pet> getPets() {
        return Collections.unmodifiableSet(pets);
    }

    @Override
    public boolean add(Pet pet) {
        return this.pets.add(pet);
    }

    @Override
    public void clear() {
        this.pets.clear();
    }

}
