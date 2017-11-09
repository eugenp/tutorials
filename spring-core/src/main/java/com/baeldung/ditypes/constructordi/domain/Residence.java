package com.baeldung.ditypes.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Residence {

    private Bedroom bedRoom;
    private Livingroom livingRoom;
    private Restroom restRoom;

    @Autowired
    public Residence(Livingroom livingRoom, Bedroom bedroom, Restroom restroom) {
        this.bedRoom = bedroom;
        this.restRoom = restroom;
        this.livingRoom = livingRoom;
    }

    @Override
    public String toString() {
        return "Residence [bedRoom=" + bedRoom + ", livingRoom=" + livingRoom + ", restRoom=" + restRoom + "]";
    }

}
