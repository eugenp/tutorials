package com.baeldung.springsecuredsockets.controllers;

import com.baeldung.springsecuredsockets.domain.Pet;
import com.baeldung.springsecuredsockets.services.PetService;
import com.baeldung.springsecuredsockets.transfer.pet.PetResponse;
import com.baeldung.springsecuredsockets.transfer.pet.PetTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/secured/pet")
public class PetController {

    Logger log = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    private PetResponse generateSingleTransfer(Pet pet) {
        PetResponse response = new PetResponse();
        response.addPet(new PetTransfer(pet.getName(), pet.getTypeString(), pet.getPetDetail().getDescription()));
        log.debug("++++++++++++++++++++: " + response);
        return response;
    }

    private PetResponse generateMultiTransfer(List<Pet> pets) {
        PetResponse response = new PetResponse();
        for (Pet pet : pets) {
            response.addPet(new PetTransfer(pet.getName(), pet.getType().toString(), pet.getPetDetail().getDescription()));
        }

        return response;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PetResponse fetchPets() {
       return generateMultiTransfer(petService.fetchPets());
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public PetResponse getPetById(@RequestParam(value = "id") Long id) {
        return generateSingleTransfer(petService.fetchPetById(id));
    }

    @RequestMapping(value = "/dragon", method = RequestMethod.POST)
    public PetResponse transmuteDragon(@RequestParam(value = "id") Long id) {
        return generateSingleTransfer(petService.transmuteDragon(id));
    }
}