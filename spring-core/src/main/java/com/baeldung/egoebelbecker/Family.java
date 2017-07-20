package com.baeldung.egoebelbecker;

import org.springframework.beans.factory.annotation.Autowired;

public class Family {

    @Autowired
    private Pet familyPet;

    public String getPetId() {
        return familyPet.speciesByName();
    }

}
