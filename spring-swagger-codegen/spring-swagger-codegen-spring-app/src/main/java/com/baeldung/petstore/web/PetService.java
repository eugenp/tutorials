package com.baeldung.petstore.web;

import java.util.List;

import com.baeldung.petstore.client.model.Pet;

public interface PetService {

    List<Pet> findAvailablePets();
    
    List<Pet> findPendingPets();
    
    List<Pet> findSoldPets();
    
}
