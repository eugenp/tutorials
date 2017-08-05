package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Animal class is injected to Zoo class using constructor-based DI.
 */
public class Zoo {

    private Animal animal;

    @Autowired
    public Zoo(Animal animal) {
        this.animal = animal;
    }

}
