package com.baeldung.ha.domain;

public class Antibody {
    private Antigen antigen;
    private int effort;

    public Antibody(Antigen antigen, int effort) {
        this.antigen = antigen;
        this.effort = effort;
    }

    public Antigen getAntigen() {
        return antigen;
    }

    public int getEffort() {
        return effort;
    }

    @Override
    public String toString() {
        return "Antibody for " + "antigen=" + antigen.getValue() + " with effort=" + effort;
    }
}
