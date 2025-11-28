package com.baeldung.dto;

public class AncestorDto {

    private String firstName;

    private Double weight;

    private String vatNumber;

    public AncestorDto() {
    }

    public AncestorDto(String firstName, Double weight, String vatNumber) {
        this.firstName = firstName;
        this.weight = weight;
        this.vatNumber = vatNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
