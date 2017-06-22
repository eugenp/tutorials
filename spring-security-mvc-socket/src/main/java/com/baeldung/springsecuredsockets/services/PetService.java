package com.baeldung.springsecuredsockets.services;

import com.baeldung.springsecuredsockets.domain.Pet;
import com.baeldung.springsecuredsockets.repositories.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    Logger log = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository petRepository;

    public Pet transmuteDragon(Long id) {
        Pet pet = petRepository.findOne(id);
        pet.setType(Pet.PetType.DRAGON);
        return pet;
    }

    public List<Pet> fetchPets() {
        List<Pet> pets = new ArrayList<>();
        petRepository.findAll().forEach(pets::add);
        log.debug("++++++++++++++++++++++++++++++: " + pets.toString());
        return pets;
    }

    public Pet fetchPetById(Long id) {
        return petRepository.findOne(id);
    }
}
