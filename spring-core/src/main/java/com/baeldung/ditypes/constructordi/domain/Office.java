package com.baeldung.ditypes.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Office {

    private Cubicle cubicle;
    private Restroom restroom;

    @Autowired
    public Office(Cubicle cubicle, Restroom restroom) {
        super();
        this.cubicle = cubicle;
        this.restroom = restroom;
    }

    @Override
    public String toString() {
        return "Office [cubicle=" + cubicle + ", restroom=" + restroom + "]";
    }

}
