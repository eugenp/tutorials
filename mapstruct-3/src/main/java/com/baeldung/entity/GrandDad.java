package com.baeldung.entity;

public class GrandDad {

    private Identity identity;

    private String weight;

    private String vatNumber;

    public GrandDad() {
    }

    public GrandDad(Identity identity, String weight, String vatNumber) {
        this.identity = identity;
        this.weight = weight;
        this.vatNumber = vatNumber;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
