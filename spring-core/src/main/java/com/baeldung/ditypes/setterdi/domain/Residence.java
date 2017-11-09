package com.baeldung.ditypes.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Residence {

    private Bedroom bedRoom;
    private Livingroom livingRoom;
    private Restroom restRoom;

    public Residence() {

    }

    @Autowired
    public void setBedRoom(Bedroom bedRoom) {
        this.bedRoom = bedRoom;
    }

    @Autowired
    public void setLivingRoom(Livingroom livingRoom) {
        this.livingRoom = livingRoom;
    }

    @Autowired
    public void setRestRoom(Restroom restRoom) {
        this.restRoom = restRoom;
    }

    @Override
    public String toString() {
        return "Residence [bedRoom=" + bedRoom + ", livingRoom=" + livingRoom + ", restRoom=" + restRoom + "]";
    }

}
