package com.baeldung.list.entity;

public class ManufacturingPlantDto {
    private String plantName;
    private String location;

    public ManufacturingPlantDto(String plantName, String location) {
        this.plantName = plantName;
        this.location = location;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
