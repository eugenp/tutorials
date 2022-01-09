package com.baeldung.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class Region {

    @Lazy
    @Autowired
    private City city;

    public Region() {
        System.out.println("Region bean initialized");
    }

    public City getCityInstance() {
        return city;
    }
}
