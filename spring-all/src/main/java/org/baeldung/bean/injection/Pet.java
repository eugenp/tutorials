package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class Pet {

    @Autowired
    private Owner owner;

    public Pet() {
    }

    public Pet(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
