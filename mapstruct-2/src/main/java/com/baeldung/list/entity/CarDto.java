package com.baeldung.list.entity;

import java.util.List;

public class CarDto {
    private String make;
    private String model;
    private int year;
    private int numberOfSeats;

    private List<ManufacturingPlantDto> manufacturingPlantDtos;

    public CarDto(String make, String model, int year, int numberOfSeats) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.numberOfSeats = numberOfSeats;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<ManufacturingPlantDto> getManufacturingPlantDtos() {
        return manufacturingPlantDtos;
    }

    public void setManufacturingPlantDtos(List<ManufacturingPlantDto> manufacturingPlantDtos) {
        this.manufacturingPlantDtos = manufacturingPlantDtos;
    }


    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
}
