package com.baeldung.openapi.petstore.controller;

import com.baeldung.openapi.api.PetApi;
import com.baeldung.openapi.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PetController implements PetApi {

    public ResponseEntity<List<Pet>> findPetsByName(String name) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
