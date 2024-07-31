package com.baeldung.graphqlreturnmap.entity;

public class Attribute {
    private String name;
    private String description;
    private String unit;

    public Attribute(String name, String description, String unit){
        this.name = name;
        this.description = description;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
