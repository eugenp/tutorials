package com.baeldung.hibernate.pojo.inheritance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal {

    @Id
    private long animalId;

    private String species;
    
    public Animal() {}    

    public Animal(long animalId, String species) {
        this.animalId = animalId;
        this.species = species;
    }

    public long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(long animalId) {
        this.animalId = animalId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

}
