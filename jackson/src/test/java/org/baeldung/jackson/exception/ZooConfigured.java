package org.baeldung.jackson.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ZooConfigured {
    public AnimalConfigured animal;
}

@JsonDeserialize(as = CatConfigured.class)
abstract class AnimalConfigured {
    public String name;

    protected AnimalConfigured() {
    }
}

class CatConfigured extends AnimalConfigured {
    public int lives;

    public CatConfigured() {
    }
}