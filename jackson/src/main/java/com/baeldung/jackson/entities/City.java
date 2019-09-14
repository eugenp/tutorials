package com.baeldung.jackson.entities;

import com.baeldung.jackson.enums.Distance;

public class City {
    
    private Distance distance;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "City [distance=" + distance + "]";
    }    
    
}
