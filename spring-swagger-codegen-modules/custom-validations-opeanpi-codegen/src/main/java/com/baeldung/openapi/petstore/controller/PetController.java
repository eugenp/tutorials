package com.baeldung.openapi.petstore.controller;

import com.baeldung.openapi.api.PetsApi;
import com.baeldung.openapi.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class PetController implements PetsApi {

    public ResponseEntity<List<Pet>> findPetsByName(String name) {
        return ResponseEntity.ok(Arrays.asList(new Pet().id(1L).name(name)));
    }
}
