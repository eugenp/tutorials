package com.baeldung.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ZooConfigured {
    public AnimalConfigured animal;

    public ZooConfigured() {
    }
}

@JsonDeserialize(as = CatConfigured.class)
abstract class AnimalConfigured {
    public String name;

    public AnimalConfigured() {
    }
}

class CatConfigured extends AnimalConfigured {
    public int lives;

    public CatConfigured() {
    }
}