package com.baeldung.collection;

/**
 * Created by Gebruiker on 5/22/2018.
 */
public class BaeldungBean {

    private String name;

    public BaeldungBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
