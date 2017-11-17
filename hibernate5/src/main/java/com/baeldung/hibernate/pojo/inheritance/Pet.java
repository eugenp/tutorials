package com.baeldung.hibernate.pojo.inheritance;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "petId")
public class Pet extends Animal {
    private String name;

    public Pet() {
    }

    public Pet(long animalId, String species, String name) {
        super(animalId, species);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
