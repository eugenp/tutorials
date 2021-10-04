package com.baeldung.openapi.petstore.controller;

import com.baeldung.openapi.petstore.PetsApi;
import com.baeldung.openapi.petstore.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * It will not have an actual implementation.
 * It's purpose is to demonstrate the
 * API swagger generator with Spring validations.
 */
@Controller("petsController")
public class PetsController implements PetsApi {

    /**
     * Retrieve a list of pets by name.
     * @param name Tags to filter by (optional)
     * @return the matching pets.
     */
    public ResponseEntity<List<Pet>> findPetsByName(String name) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Add a new pet in our pets store.
     * @param pet  (required)
     * @return the id of the pet
     */
    public ResponseEntity<Pet> addPet(Pet pet) {
        Long id = Double.valueOf(Math.random() * 100).longValue();
        return ResponseEntity.ok(new Pet().id(id));
    }

}
