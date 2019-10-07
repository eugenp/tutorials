package com.baeldung.petstore.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.petstore.client.api.PetApi;
import com.baeldung.petstore.client.model.Pet;

@Service
public class DefaultPetService implements PetService {

    @Autowired
    private PetApi petApi;

    public List<Pet> findAvailablePets() {
        return petApi.findPetsByStatus(Arrays.asList("available"));
    }

    public List<Pet> findPendingPets() {
        return petApi.findPetsByStatus(Arrays.asList("pending"));
    }

    public List<Pet> findSoldPets() {
        return petApi.findPetsByStatus(Arrays.asList("sold"));
    }
    
}
