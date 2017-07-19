package com.baeldung.egoebelbecker;

import org.springframework.beans.factory.annotation.Autowired;

public class Family {

    private Pet familyPet;

    @Autowired
    public Family(Pet pet) {
        familyPet = pet;
    }

    @Autowired
    public void setFamilyPet(Pet pet) {
        familyPet = pet;
    }

    public String getPetId() {
        return familyPet.speciesByName();
    }

}
