package com.baeldung.inmemory.persistence.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class LocationTag {
    private String name;
    private int xPos;
    private int yPos;

    public LocationTag() {
    }

    public LocationTag(String name, int xPos, int yPos) {
        super();
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getName() {
        return name;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
