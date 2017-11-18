package com.baeldung.model;

import org.kie.api.definition.type.Position;

public class Beatle {

    @Position(0)
    private String lastName;
    @Position(1)
    private String instrument;

    public Beatle(String lastName, String instrument) {
        this.lastName = lastName;
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}