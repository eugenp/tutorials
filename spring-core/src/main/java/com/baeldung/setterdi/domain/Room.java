package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Room {

    private Bed bed;

    @Autowired
    public void setBed(Bed bed) {
        this.bed = bed;
    }

}
