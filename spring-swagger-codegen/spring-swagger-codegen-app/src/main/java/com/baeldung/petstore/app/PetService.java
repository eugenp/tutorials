package com.baeldung.petstore.app;

import java.util.List;

import com.baeldung.petstore.client.model.Pet;

public interface PetService {

    /**
     * Find available pets
     * @return List of available pets
     */
    List<Pet> findAvailablePets();

    /**
     * Find Pending pets
     * @return List of pending pets
     */
    List<Pet> findPendingPets();
    
    /**
     * Find sold pets
     * @return List of sold pets
     */
    List<Pet> findSoldPets();
    
}
