package com.baeldung.sampleapp.web.dto;

public class PactDto {

    private boolean condition;
    private String name;

    public PactDto() {
    }

    public PactDto(boolean condition, String name) {
        super();
        this.condition = condition;
        this.name = name;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
