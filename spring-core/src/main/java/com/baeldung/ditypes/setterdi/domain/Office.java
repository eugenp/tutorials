package com.baeldung.ditypes.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Office {

    private Cubicle cubicle;
    private Restroom restroom;

    public Office() {
    }

    @Autowired
    public void setCubicle(Cubicle cubicle) {
        this.cubicle = cubicle;
    }

    @Autowired
    public void setRestroom(Restroom restroom) {
        this.restroom = restroom;
    }

    @Override
    public String toString() {
        return "Office [cubicle=" + cubicle + ", restroom=" + restroom + "]";
    }

}
