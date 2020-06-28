package com.baeldung;

import java.io.Serializable;

public class Ledger implements Serializable {

    public Ledger() {
    }

    public Ledger(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
