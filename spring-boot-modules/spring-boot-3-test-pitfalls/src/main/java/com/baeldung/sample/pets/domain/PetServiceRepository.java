package com.baeldung.sample.pets.domain;

import java.util.Collection;

public interface PetServiceRepository {

    boolean add(Pet pet);

    void clear();

    Collection<Pet> getPets();

}
